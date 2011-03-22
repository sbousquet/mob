package com.netappsid.mob.ejb3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.transaction.UserTransaction;
import javax.xml.transform.TransformerFactory;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netappsid.mob.ejb3.jndi.UserTransactionFactory;
import com.netappsid.mob.ejb3.jndi.UserTransactionRef;
import com.netappsid.mob.ejb3.osgi.DeployOSGIEJB3Bundle;
import com.netappsid.mob.ejb3.osgi.EJB3BundleDeployer;
import com.netappsid.mob.ejb3.osgi.EJB3ExecutorService;
import com.netappsid.mob.ejb3.osgi.OSGIServiceLookup;
import com.netappsid.mob.ejb3.xml.PersistenceUnitUtils;

/**
 * 
 * @author NetAppsID Inc.
 * 
 */
public class MobPlugin implements BundleActivator
{
	private static MobPlugin instance;

	private static Logger logger = LoggerFactory.getLogger(MobPlugin.class);

	private Map<String, List<EJB3BundleDeployer>> applicationBundleDeployers = new HashMap<String, List<EJB3BundleDeployer>>();
	private RemoteServicesRegistry remoteServicesRegistry = new RemoteServicesRegistry();

	/**
	 * 
	 */
	public MobPlugin()
	{
		MobPlugin.instance = this;
	}

	public static MobPlugin getInstance()
	{
		return instance;
	}

	public static Set<String> getApplicationNames()
	{
		return instance.applicationBundleDeployers.keySet();
	}

	public static List<EJB3BundleDeployer> getApplicationBundleDeployers(String applicationName)
	{
		return instance.applicationBundleDeployers.get(applicationName);
	}

	public static RemoteServicesRegistry getRemoteServicesRegistry()
	{
		return instance.remoteServicesRegistry;
	}

	@Override
	public void start(BundleContext context) throws Exception
	{
		if (!"junit".equals(System.getProperty("naid.mode")))
		{
			initializeApplicationBundleDeployers();
			OSGIServiceLookup osgiServiceLookup = new OSGIServiceLookup(context);
			UserTransaction userTransaction = osgiServiceLookup.getService(UserTransaction.class);
			Context jndiContext = osgiServiceLookup.getService(Context.class);
			bindUserTransaction(jndiContext, userTransaction);
			PackageAdmin packageAdmin = osgiServiceLookup.getService(PackageAdmin.class);

			PersistenceUnitUtils persistenceUnitUtils = new PersistenceUnitUtils(context, osgiServiceLookup.getService(IExtensionRegistry.class),
					TransformerFactory.newInstance());

			DeployOSGIEJB3Bundle deployOSGIEJB3Bundle = new DeployOSGIEJB3Bundle(new EJB3ExecutorService(), userTransaction, jndiContext,
					osgiServiceLookup.getService(JPAProviderFactory.class), osgiServiceLookup.getService(DatasourceProvider.class), persistenceUnitUtils,
					packageAdmin);

			for (Map.Entry<String, List<EJB3BundleDeployer>> entry : applicationBundleDeployers.entrySet())
			{
				deployOSGIEJB3Bundle.deploy(entry.getKey(), entry.getValue());
			}
		}
	}

	private void bindUserTransaction(Context context, UserTransaction transaction)
	{
		try
		{
			Context javaContext = context;
			try
			{
				javaContext = (Context) javaContext.lookup("java:");
			}
			catch (NamingException e)
			{
				javaContext = javaContext.createSubcontext("java:");
			}

			RefAddr ra = new UserTransactionRef("UserTransaction", transaction);

			Reference ref = new Reference(UserTransaction.class.getName(), new StringRefAddr("name", "UserTransaction"),
					UserTransactionFactory.class.getName(), UserTransactionFactory.class.getResource("/").toString());
			ref.add(ra);

			javaContext.rebind("UserTransaction", ref);
		}
		catch (NamingException e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception
	{}

	private void initializeApplicationBundleDeployers()
	{
		for (IConfigurationElement configurationElement : getEJB3DeployerConfigurationElements())
		{
			final String applicationName = configurationElement.getAttribute("applicationName");
			final String packageRestriction = configurationElement.getAttribute("packageRestriction");
			final Bundle bundle = Platform.getBundle(configurationElement.getContributor().getName());

			// extra classes
			IConfigurationElement[] children = configurationElement.getChildren("extra");
			List<Class<?>> extraClasses = new ArrayList<Class<?>>();

			for (IConfigurationElement iConfigurationElement : children)
			{
				String ejbClass = iConfigurationElement.getAttribute("ejbClass");
				try
				{
					extraClasses.add(bundle.loadClass(ejbClass));
				}
				catch (ClassNotFoundException e)
				{
					logger.error(e.getMessage(), e);
				}
			}

			final EJB3BundleDeployer deployer = new EJB3BundleDeployer(bundle, packageRestriction, extraClasses);

			if (!applicationBundleDeployers.containsKey(applicationName))
			{
				applicationBundleDeployers.put(applicationName, new ArrayList<EJB3BundleDeployer>());
			}

			applicationBundleDeployers.get(applicationName).add(deployer);
		}
	}

	private IConfigurationElement[] getEJB3DeployerConfigurationElements()
	{
		return Platform.getExtensionRegistry().getConfigurationElementsFor("com.netappsid.mob.ejb3.deployer");
	}

}

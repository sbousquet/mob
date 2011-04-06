package com.netappsid.mob.ejb3.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.transaction.UserTransaction;
import javax.xml.transform.TransformerFactory;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.netappsid.mob.ejb3.DatasourceProvider;
import com.netappsid.mob.ejb3.JPAProviderFactory;
import com.netappsid.mob.ejb3.jndi.UserTransactionFactory;
import com.netappsid.mob.ejb3.jndi.UserTransactionRef;
import com.netappsid.mob.ejb3.osgi.DeployOSGIEJB3Bundle;
import com.netappsid.mob.ejb3.osgi.EJB3BundleDeployer;
import com.netappsid.mob.ejb3.osgi.EJB3ExecutorService;
import com.netappsid.mob.ejb3.osgi.OSGIServiceLookup;
import com.netappsid.mob.ejb3.xml.PersistenceUnitUtils;

public class MobDeployer implements Runnable
{
	private final Logger logger = LoggerFactory.getLogger(MobDeployer.class);

	private final BundleContext context;
	private final OSGIServiceLookup<UserTransaction> userTransactionService;
	private final OSGIServiceLookup<Context> contextService;
	private final OSGIServiceLookup<PackageAdmin> packageAdminService;
	private final OSGIServiceLookup<IExtensionRegistry> extensionRegistryService;
	private final OSGIServiceLookup<JPAProviderFactory> jpaProviderFactoryService;
	private final OSGIServiceLookup<DatasourceProvider> datasourceProviderService;

	public MobDeployer(BundleContext context, OSGIServiceLookup<UserTransaction> userTransactionService, OSGIServiceLookup<Context> contextService,
			OSGIServiceLookup<PackageAdmin> packageAdminService, OSGIServiceLookup<IExtensionRegistry> extensionRegistryService,
			OSGIServiceLookup<JPAProviderFactory> jpaProviderFactoryService, OSGIServiceLookup<DatasourceProvider> datasourceProviderService)
	{
		this.context = context;
		this.userTransactionService = userTransactionService;
		this.contextService = contextService;
		this.packageAdminService = packageAdminService;
		this.extensionRegistryService = extensionRegistryService;
		this.jpaProviderFactoryService = jpaProviderFactoryService;
		this.datasourceProviderService = datasourceProviderService;
	}

	@Override
	public void run()
	{
		UserTransaction userTransaction = userTransactionService.getService();
		Context jndiContext = contextService.getService();
		bindUserTransaction(jndiContext, userTransaction);
		PackageAdmin packageAdmin = packageAdminService.getService();

		PersistenceUnitUtils persistenceUnitUtils = new PersistenceUnitUtils(context, extensionRegistryService.getService(), TransformerFactory.newInstance());

		DeployOSGIEJB3Bundle deployOSGIEJB3Bundle = new DeployOSGIEJB3Bundle(new EJB3ExecutorService(), userTransaction, jndiContext,
				jpaProviderFactoryService.getService(), datasourceProviderService.getService(), persistenceUnitUtils, packageAdmin);

		ArrayListMultimap<String, EJB3BundleDeployer> applicationBundleDeployers = getApplicationBundleDeployers(packageAdmin);

		for (Entry<String, Collection<EJB3BundleDeployer>> entry : applicationBundleDeployers.asMap().entrySet())
		{
			deployOSGIEJB3Bundle.deploy(entry.getKey(), entry.getValue());
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

	private ArrayListMultimap<String, EJB3BundleDeployer> getApplicationBundleDeployers(PackageAdmin packageAdmin)
	{

		ArrayListMultimap<String, EJB3BundleDeployer> applicationBundleDeployers = ArrayListMultimap.create();

		for (IConfigurationElement configurationElement : getEJB3DeployerConfigurationElements())
		{
			final String applicationName = configurationElement.getAttribute("applicationName");
			final String packageRestriction = configurationElement.getAttribute("packageRestriction");

			final Bundle bundle = packageAdmin.getBundles(configurationElement.getContributor().getName(), null)[0];

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
			applicationBundleDeployers.put(applicationName, deployer);
		}

		return applicationBundleDeployers;
	}

	private IConfigurationElement[] getEJB3DeployerConfigurationElements()
	{
		return extensionRegistryService.getService().getConfigurationElementsFor("com.netappsid.mob.ejb3.deployer");
	}

}

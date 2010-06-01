package com.netappsid.mob.ejb3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netappsid.mob.ejb3.osgi.DeployOSGIEJB3Bundle;
import com.netappsid.mob.ejb3.osgi.EJB3BundleDeployer;

/**
 * 
 * @author NetAppsID Inc.
 * 
 */
public class MobPlugin extends Plugin
{
	private static MobPlugin instance;

	private static Logger logger = LoggerFactory.getLogger(MobPlugin.class);

	private Map<String, List<EJB3BundleDeployer>> applicationBundleDeployers = new HashMap<String, List<EJB3BundleDeployer>>();
	private RemoteServicesRegistry remoteServicesRegistry = new RemoteServicesRegistry();

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
		super.start(context);

		MobPlugin.instance = this;
		initializeApplicationBundleDeployers();

		if ("server".equals(System.getProperty("naid.mode")) || "standalone".equals(System.getProperty("naid.mode"))
				|| "true".equals(System.getProperty("naid.dev")))
		{
			for (Map.Entry<String, List<EJB3BundleDeployer>> entry : applicationBundleDeployers.entrySet())
			{
				DeployOSGIEJB3Bundle.deploy(entry.getKey(), entry.getValue());
			}
		}
	}

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
		return Platform.getExtensionRegistry().getConfigurationElementsFor("com.netappsid.ejb3.deployer");
	}

	public static PackageAdmin getPackageAdmin()
	{
		return getService(PackageAdmin.class);
	}

	public static <T> T getService(Class<T> seviceInterface)
	{
		BundleContext bundleContext = instance.getBundle().getBundleContext();
		return (T) bundleContext.getService(bundleContext.getServiceReference(seviceInterface.getName()));
	}

}

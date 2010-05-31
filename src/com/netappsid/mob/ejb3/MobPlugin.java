package com.netappsid.mob.ejb3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;

import com.netappsid.mob.ejb3.osgi.BundleDeployer;
import com.netappsid.mob.ejb3.osgi.DeployOSGIEJB3Bundle;

/**
 * 
 * @author NetAppsID Inc.
 * 
 */
public class MobPlugin extends Plugin
{
	private static MobPlugin instance;

	private static Logger logger = Logger.getLogger(MobPlugin.class);

	private Map<String, List<BundleDeployer>> applicationBundleDeployers = new HashMap<String, List<BundleDeployer>>();
	private RemoteServicesRegistry remoteServicesRegistry = new RemoteServicesRegistry();

	public static MobPlugin getInstance()
	{
		return instance;
	}

	public static Set<String> getApplicationNames()
	{
		return instance.applicationBundleDeployers.keySet();
	}

	public static List<BundleDeployer> getApplicationBundleDeployers(String applicationName)
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

		if ("server".equals(System.getProperty("naid.mode")) || "standalone".equals(System.getProperty("naid.mode")) || "true".equals(System.getProperty("naid.dev")))
		{
			for (Map.Entry<String, List<BundleDeployer>> entry : applicationBundleDeployers.entrySet())
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
			
			//extra classes
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
					logger.error(e, e);
				}
			}
			
			final BundleDeployer deployer = new BundleDeployer(bundle, packageRestriction,extraClasses);

			if (!applicationBundleDeployers.containsKey(applicationName))
			{
				applicationBundleDeployers.put(applicationName, new ArrayList<BundleDeployer>());
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
		BundleContext bundleContext = instance.getBundle().getBundleContext();
		return (PackageAdmin) bundleContext.getService(bundleContext.getServiceReference(PackageAdmin.class.getName()));
	}

}

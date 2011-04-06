package com.netappsid.mob.ejb3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.transaction.UserTransaction;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;

import com.netappsid.mob.ejb3.internal.MobDeployer;
import com.netappsid.mob.ejb3.osgi.EJB3BundleDeployer;
import com.netappsid.mob.ejb3.osgi.OSGIServiceLookup;

/**
 * 
 * @author NetAppsID Inc.
 * 
 */
public class MobPlugin implements BundleActivator
{
	private static MobPlugin instance;

	private Map<String, List<EJB3BundleDeployer>> applicationBundleDeployers = new HashMap<String, List<EJB3BundleDeployer>>();
	private RemoteServicesRegistry remoteServicesRegistry = new RemoteServicesRegistry();

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
		final OSGIServiceLookup<UserTransaction> userTransactionService = new OSGIServiceLookup<UserTransaction>(context, UserTransaction.class);
		final OSGIServiceLookup<Context> contextService = new OSGIServiceLookup<Context>(context, Context.class);
		final OSGIServiceLookup<PackageAdmin> packageAdminService = new OSGIServiceLookup<PackageAdmin>(context, PackageAdmin.class);
		final OSGIServiceLookup<IExtensionRegistry> extensionRegistryService = new OSGIServiceLookup<IExtensionRegistry>(context, IExtensionRegistry.class);
		final OSGIServiceLookup<JPAProviderFactory> jpaProviderFactoryService = new OSGIServiceLookup<JPAProviderFactory>(context, JPAProviderFactory.class);
		final OSGIServiceLookup<DatasourceProvider> datasourceProviderService = new OSGIServiceLookup<DatasourceProvider>(context, DatasourceProvider.class);

		new Thread(new MobDeployer(context, userTransactionService, contextService, packageAdminService, extensionRegistryService, jpaProviderFactoryService,
				datasourceProviderService), "MobDeployer").start();
	}

	@Override
	public void stop(BundleContext context) throws Exception
	{

	}
}

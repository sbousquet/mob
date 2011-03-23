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

import com.netappsid.mob.ejb3.internal.MobDeployer;
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
			new Thread(new MobDeployer(context, new OSGIServiceLookup(context)), "MobDeployer").start();
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

}

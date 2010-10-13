package org.apache.naming;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.naming.spi.ObjectFactory;
import javax.naming.spi.ObjectFactoryBuilder;

import org.apache.log4j.Logger;
import org.apache.naming.java.javaURLContextFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;

public class NamingPlugin implements BundleActivator
{

	private static Logger logger = Logger.getLogger(NamingPlugin.class);
	private BundleContext context;
	private static NamingPlugin instance;

	/**
	 * 
	 */
	public NamingPlugin()
	{
		instance = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception
	{
		this.context = context;
		
		NamingManager.setInitialContextFactoryBuilder(new InitialContextFactoryBuilder()
			{

				@Override
				public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> environment) throws NamingException
				{
					String factory = (String) environment.get("java.naming.factory.initial");
					if (factory != null)
					{
						if (factory.equals(javaURLContextFactory.class.getName()))
						{
							return new org.apache.naming.java.javaURLContextFactory();
						}
						else
						{
							PackageAdmin packageAdmin = NamingPlugin.getPackageAdmin();
							try
							{
								return (InitialContextFactory) packageAdmin.getExportedPackage(factory.substring(0, factory.lastIndexOf('.')))
										.getExportingBundle().loadClass(factory).newInstance();
							}
							catch (Exception e)
							{
								logger.error(e, e);
							}
						}
					}
					return new org.apache.naming.java.javaURLContextFactory();
				}
			});

		NamingManager.setObjectFactoryBuilder(new ObjectFactoryBuilder()
			{

				@Override
				public ObjectFactory createObjectFactory(Object obj, Hashtable<?, ?> environment) throws NamingException
				{
					if (obj instanceof Reference)
					{
						String factoryClassName = ((Reference) obj).getFactoryClassName();
						PackageAdmin packageAdmin = NamingPlugin.getPackageAdmin();
						ExportedPackage exportedPackage = packageAdmin.getExportedPackage(factoryClassName.substring(0, factoryClassName.lastIndexOf('.')));

						if (exportedPackage != null)
						{
							try
							{
								return (ObjectFactory) exportedPackage.getExportingBundle().loadClass(factoryClassName).newInstance();
							}
							catch (Exception e)
							{
								logger.error(e, e);
							}
						}
					}

					return null;
				}
			});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception
	{
	}

	public static PackageAdmin getPackageAdmin()
	{
		return getService(PackageAdmin.class);
	}

	public static <T> T getService(Class<T> seviceInterface)
	{
		BundleContext bundleContext = instance.getContext();
		return (T) bundleContext.getService(bundleContext.getServiceReference(seviceInterface.getName()));
	}

	/**
	 * @return the context
	 */
	public BundleContext getContext()
	{
		return context;
	}
}

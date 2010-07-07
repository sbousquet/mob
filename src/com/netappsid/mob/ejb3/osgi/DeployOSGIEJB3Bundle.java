/**
 * 
 */
package com.netappsid.mob.ejb3.osgi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.persistence.Entity;
import javax.transaction.UserTransaction;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.objectweb.asm.ClassReader;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netappsid.mob.ejb3.DataSourceHelper;
import com.netappsid.mob.ejb3.MobPlugin;
import com.netappsid.mob.ejb3.internal.EJB3ThreadWorker;
import com.netappsid.mob.ejb3.internal.EJb3AnnotationVisitor;
import com.netappsid.mob.ejb3.internal.classloader.MultiBundleClassLoader;
import com.netappsid.mob.ejb3.jndi.UserTransactionFactory;
import com.netappsid.mob.ejb3.jndi.UserTransactionRef;
import com.netappsid.mob.ejb3.xml.EjbJarXml;
import com.netappsid.mob.ejb3.xml.PersistenceUnitInfoXml;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.18 $
 */
public class DeployOSGIEJB3Bundle
{
	private static Logger logger = LoggerFactory.getLogger(DeployOSGIEJB3Bundle.class);

	private static ExecutorService executorService;

	private static boolean isInit = false;

	private static boolean userTransactionBind;

	/**
	 * 
	 */
	private DeployOSGIEJB3Bundle()
	{}

	public static void init()
	{
		if (!isInit)
		{
			executorService = Executors.newCachedThreadPool(new ThreadFactory()
				{

					@Override
					public Thread newThread(Runnable r)
					{
						return new EJB3ThreadWorker(r);
					}
				});

			isInit = true;
		}
		
		// bind the usertransaction manager
		if (!userTransactionBind)
		{

			try
			{
				Context javaContext = MobPlugin.getService(Context.class);
				try
				{
					javaContext = (Context) javaContext.lookup("java:");
				}
				catch (NamingException e)
				{
					javaContext = javaContext.createSubcontext("java:");
				}

				UserTransaction transaction = MobPlugin.getService(UserTransaction.class);
				RefAddr ra = new UserTransactionRef("UserTransaction", transaction);

				Reference ref = new Reference(UserTransaction.class.getName(), new StringRefAddr("name", "UserTransaction"), UserTransactionFactory.class
						.getName(), UserTransactionFactory.class.getResource("/").toString());
				ref.add(ra);

				javaContext.rebind("UserTransaction", ref);

			}
			catch (NamingException e)
			{
				logger.error(e.getMessage(), e);
			}
			userTransactionBind = true;
		}

	}

	public static ExecutorService getExecutorService()
	{
		return executorService;
	}

	public static void deploy(Bundle bundle, String baseName, String packageRestriction) throws ClassNotFoundException
	{
		deploy(bundle, baseName, packageRestriction, new ArrayList<Class<?>>());
	}

	public static void deploy(Bundle bundle, String baseName, String packageRestriction, List<Class<?>> ejb3ClassList) throws ClassNotFoundException
	{
		EJB3BundleDeployer bundleDeployer = new EJB3BundleDeployer(bundle, packageRestriction, ejb3ClassList);
		deploy(baseName, Arrays.asList(bundleDeployer));
	}

	public static void deploy(String applicationName, List<EJB3BundleDeployer> bundleDeployers)
	{
		init();

		EJB3Deployer deployer = new EJB3Deployer(executorService, applicationName);
		Set<Bundle> bundles = new HashSet<Bundle>();

		for (EJB3BundleDeployer bundleDeployer : bundleDeployers)
		{
			bundles.add(bundleDeployer.getBundle());

			// find datasource
			Enumeration<URL> datasources = bundleDeployer.getBundle().findEntries("/", "*-ds.xml", true);
			if (datasources != null)
			{
				while (datasources.hasMoreElements())
				{
					URL url = datasources.nextElement();
					try
					{
						DataSourceHelper.parseXmlDataSourceAndBindIt(new SAXReader().read(url.openStream()), MobPlugin.getService(Context.class));
					}
					catch (Exception e)
					{
						logger.error(e.getMessage(), e);
					}

				}
			}

			if (deployer.getPersistenceUnitInfoXml() == null)
			{
				// find persistence.xml
				Enumeration<URL> persistenceFiles = bundleDeployer.getBundle().findEntries("/", "persistence.xml", true);

				if (persistenceFiles != null)
				{
					while (persistenceFiles.hasMoreElements())
					{
						URL url = persistenceFiles.nextElement();
						PersistenceUnitInfoXml persistenceUnitInfoXml = new PersistenceUnitInfoXml();
						try
						{
							persistenceUnitInfoXml.fromInputStream(url.openStream());
						}
						catch (DocumentException e)
						{
							logger.error(e.getMessage(), e);
						}
						catch (IOException e)
						{
							logger.error(e.getMessage(), e);
						}
						deployer.setPersistenceUnitInfoXml(persistenceUnitInfoXml);

						// only one by deployment
						break;
					}
				}
			}

			// find ejb-jar.xml
			Enumeration<URL> ejbJarFiles = bundleDeployer.getBundle().findEntries("/", "ejb-jar.xml", true);

			if (ejbJarFiles != null)
			{
				while (ejbJarFiles.hasMoreElements())
				{
					URL url = (URL) ejbJarFiles.nextElement();
					EjbJarXml ejbJarXml = new EjbJarXml();
					try
					{
						ejbJarXml.fromInputStream(url.openStream());
					}
					catch (DocumentException e)
					{
						logger.error(e.getMessage(), e);
					}
					catch (IOException e)
					{
						logger.error(e.getMessage(), e);
					}

					deployer.setEjbJarXml(ejbJarXml);

				}
			}

			// find EJB3 service and Bean
			Enumeration<URL> enumeration = bundleDeployer.getBundle().findEntries("/", "*.class", true);

			while (enumeration.hasMoreElements())
			{
				URL url = enumeration.nextElement();

				String name = url.getPath().replace('/', '.');

				if (name.startsWith(".bin."))
				{
					name = name.substring(".bin.".length());
				}
				else if (name.startsWith("."))
				{
					name = name.substring(".".length());
				}

				if (name.endsWith(".class"))
				{
					name = name.substring(0, name.length() - ".class".length());
				}

				// skip class with a package restriction
				if (bundleDeployer.getPackageRestriction() != null && !bundleDeployer.getPackageRestriction().equals(""))
				{
					if (!name.startsWith(bundleDeployer.getPackageRestriction()))
					{
						continue;
					}
				}

				if (isEJB3Class(url))
				{
					try
					{
						Class<?> clazz = bundleDeployer.getBundle().loadClass(name);

						if (isEJB3Entity(clazz))
						{
							deployer.addEntity(clazz);
						}
						else if (isEJB3Service(clazz))
						{
							deployer.addService(clazz, url);
						}
					}
					catch (Exception e)
					{
						logger.error(e.getMessage(), e);
					}
				}

				if (bundleDeployer.getEjb3ClassList() != null)
				{
					// explicit class to deploy
					for (Class clazz : bundleDeployer.getEjb3ClassList())
					{
						if (isEJB3Entity(clazz))
						{
							deployer.addEntity(clazz);
						}
						else if (isEJB3Service(clazz))
						{
							deployer.addService(clazz, null);
						}
					}
				}

			}
		}

		deployer.setClassLoader(new MultiBundleClassLoader(bundles));

		deployer.deploy();
	}

	/**
	 * @param url
	 * @return
	 */
	private static boolean isEJB3Class(URL url)
	{
		InputStream openStream = null;
		try
		{
			openStream = url.openStream();

			ClassReader classReader = new ClassReader(openStream);

			EJb3AnnotationVisitor eJb3AnnotationVisitor = new EJb3AnnotationVisitor();
			classReader.accept(eJb3AnnotationVisitor, ClassReader.SKIP_CODE);

			return eJb3AnnotationVisitor.isEjbClass();
		}
		catch (IOException e)
		{
			logger.error(e.getMessage(), e);
		}
		finally
		{
			if (openStream != null)
			{
				try
				{
					openStream.close();
				}
				catch (IOException e)
				{
					logger.error(e.getMessage(), e);
				}
			}
		}

		return false;
	}

	public static boolean isEJB3Service(Class<?> class1)
	{
		return class1.isAnnotationPresent(Stateless.class) || class1.isAnnotationPresent(Stateful.class);
	}

	public static boolean isEJB3Entity(Class<?> class1)
	{
		return class1.isAnnotationPresent(Entity.class);
	}

	public static boolean isLocalService(Class serviceClass)
	{
		return isServiceAnnotationPresent(serviceClass, Local.class);
	}

	public static boolean isRemoteService(Class serviceClass)
	{
		return isServiceAnnotationPresent(serviceClass, Remote.class);
	}

	public static Class getLocalServiceInterface(Class serviceClass)
	{
		if (serviceClass.isAnnotationPresent(Local.class))
		{
			return ((Local) serviceClass.getAnnotation(Local.class)).value()[0];
		}
		else
		{
			for (Class type : serviceClass.getInterfaces())
			{
				if (type.isAnnotationPresent(Local.class))
				{
					return type;
				}
			}
		}

		return null;
	}

	public static Class getRemoteServiceInterface(Class serviceClass)
	{
		if (serviceClass.isAnnotationPresent(Remote.class))
		{
			return ((Remote) serviceClass.getAnnotation(Remote.class)).value()[0];
		}
		else
		{
			for (Class type : serviceClass.getInterfaces())
			{
				if (type.isAnnotationPresent(Remote.class))
				{
					return type;
				}
			}
		}

		return null;
	}

	private static boolean isServiceAnnotationPresent(Class serviceClass, Class serviceAnnotationClass)
	{
		if (serviceClass.isAnnotationPresent(serviceAnnotationClass))
		{
			return true;
		}
		else
		{
			for (Class type : serviceClass.getInterfaces())
			{
				if (type.isAnnotationPresent(serviceAnnotationClass))
				{
					return true;
				}
			}
		}

		return false;
	}
}

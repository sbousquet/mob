/**
 * 
 */
package com.netappsid.mob.ejb3.osgi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.persistence.Entity;
import javax.transaction.UserTransaction;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.objectweb.asm.ClassReader;
import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netappsid.mob.ejb3.DataSourceHelper;
import com.netappsid.mob.ejb3.DatasourceProvider;
import com.netappsid.mob.ejb3.JPAProviderFactory;
import com.netappsid.mob.ejb3.internal.EJb3AnnotationVisitor;
import com.netappsid.mob.ejb3.internal.classloader.MultiBundleClassLoader;
import com.netappsid.mob.ejb3.xml.EjbJarXml;
import com.netappsid.mob.ejb3.xml.PersistenceUnitInfoXml;
import com.netappsid.mob.ejb3.xml.PersistenceUnitUtils;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.18 $
 */
public class DeployOSGIEJB3Bundle
{
	private static Logger logger = LoggerFactory.getLogger(DeployOSGIEJB3Bundle.class);

	private final EJB3ExecutorService executorService;
	private final Context context;
	private final JPAProviderFactory jpaProviderFactory;
	private final DatasourceProvider dataSourceProvider;
	private final UserTransaction userTransaction;
	private final PersistenceUnitUtils persistenceUnitUtils;
	private final PackageAdmin packageAdmin;

	/**
	 * 
	 */
	public DeployOSGIEJB3Bundle(EJB3ExecutorService ejb3ExecutorService, UserTransaction userTransaction, Context context,
			JPAProviderFactory jpaProviderFactory, DatasourceProvider dataSourceProvider, PersistenceUnitUtils persistenceUnitUtils, PackageAdmin packageAdmin)
	{
		this.executorService = ejb3ExecutorService;
		this.userTransaction = userTransaction;
		this.context = context;
		this.jpaProviderFactory = jpaProviderFactory;
		this.dataSourceProvider = dataSourceProvider;
		this.persistenceUnitUtils = persistenceUnitUtils;
		this.packageAdmin = packageAdmin;
	}

	public void deploy(Bundle bundle, String baseName, String packageRestriction) throws ClassNotFoundException
	{
		deploy(bundle, baseName, packageRestriction, new ArrayList<Class<?>>());
	}

	public void deploy(Bundle bundle, String baseName, String packageRestriction, List<Class<?>> ejb3ClassList) throws ClassNotFoundException
	{
		EJB3BundleDeployer bundleDeployer = new EJB3BundleDeployer(bundle, packageRestriction, ejb3ClassList);
		deploy(baseName, Arrays.asList(bundleDeployer));
	}

	public void deploy(String applicationName, Collection<EJB3BundleDeployer> collection)
	{
		EJB3Deployer deployer = new EJB3Deployer(executorService, packageAdmin, userTransaction, context, jpaProviderFactory, applicationName);
		Set<Bundle> bundles = new HashSet<Bundle>();

		DataSourceHelper dataSourceHelper = new DataSourceHelper(dataSourceProvider);

		for (EJB3BundleDeployer bundleDeployer : collection)
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
						dataSourceHelper.parseXmlDataSourceAndBindIt(new SAXReader().read(url.openStream()), context);
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
						PersistenceUnitInfoXml persistenceUnitInfoXml = new PersistenceUnitInfoXml(context, persistenceUnitUtils);
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

				EJb3AnnotationVisitor ejb3Class = generateClassInfo(url);
				String className = ejb3Class.getClassName();
				
				// skip class with a package restriction
				if (bundleDeployer.getPackageRestriction() != null && !bundleDeployer.getPackageRestriction().equals(""))
				{
					if (!className.startsWith(bundleDeployer.getPackageRestriction()))
					{
						continue;
					}
				}
				
				if (ejb3Class.isEjbClass())
				{
					try
					{
						Class<?> clazz = bundleDeployer.getBundle().loadClass(className);

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

		
		MultiBundleClassLoader classLoader = new MultiBundleClassLoader(packageAdmin,bundles);
		
		deployer.setClassLoader(classLoader);

		deployer.deploy();
	}

	/**
	 * @param url
	 * @return
	 */
	private static EJb3AnnotationVisitor generateClassInfo(URL url)
	{
		InputStream openStream = null;
		try
		{
			openStream = url.openStream();

			ClassReader classReader = new ClassReader(openStream);

			EJb3AnnotationVisitor eJb3AnnotationVisitor = new EJb3AnnotationVisitor();
			classReader.accept(eJb3AnnotationVisitor, ClassReader.SKIP_CODE);

			return eJb3AnnotationVisitor;
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

		return null;
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

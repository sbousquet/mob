package com.netappsid.mob.ejb3.osgi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netappsid.mob.ejb3.JPAProvider;
import com.netappsid.mob.ejb3.JPAProviderFactory;
import com.netappsid.mob.ejb3.MobPlugin;
import com.netappsid.mob.ejb3.internal.BundleUnitManager;
import com.netappsid.mob.ejb3.internal.EJB3BundleUnit;
import com.netappsid.mob.ejb3.internal.EJb3Service;
import com.netappsid.mob.ejb3.internal.StatelessService;
import com.netappsid.mob.ejb3.internal.interceptors.Interceptors;
import com.netappsid.mob.ejb3.jndi.JNDIEntityManager;
import com.netappsid.mob.ejb3.xml.EjbJarXml;
import com.netappsid.mob.ejb3.xml.PersistenceUnitInfoXml;

/**
 * 
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 */
public class EJB3Deployer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(EJB3Deployer.class);

	private final ExecutorService executorService;
	private final String baseName;
	private final Map<String, EJb3Service> bindedEjb3Services = new HashMap<String, EJb3Service>();

	private PersistenceUnitInfoXml persistenceUnitInfoXml;
	private ClassLoader classLoader;
	private Map<Class<?>, URL> ejb3Service = new HashMap<Class<?>, URL>();
	private List<Class<?>> entityClass = new ArrayList<Class<?>>();
	private boolean deployed = false;

	private EjbJarXml ejbJarXml;

	private final Context context;
	private final JPAProviderFactory jpaProviderFactory;

	private final UserTransaction userTransaction;

	public EJB3Deployer(ExecutorService executorService, UserTransaction userTransaction, Context context, JPAProviderFactory jpaProviderFactory,
			String baseName)
	{
		this.executorService = executorService;
		this.userTransaction = userTransaction;
		this.context = context;
		this.jpaProviderFactory = jpaProviderFactory;
		this.baseName = baseName;
	}

	public PersistenceUnitInfoXml getPersistenceUnitInfoXml()
	{
		return persistenceUnitInfoXml;
	}

	public void setPersistenceUnitInfoXml(PersistenceUnitInfoXml persistenceUnitInfoXml)
	{
		this.persistenceUnitInfoXml = persistenceUnitInfoXml;
	}

	public void setClassLoader(ClassLoader classLoader)
	{
		this.classLoader = classLoader;
	}

	public void addService(Class<?> service)
	{
		ejb3Service.put(service, null);
	}

	public void addService(Class<?> service, URL url)
	{
		ejb3Service.put(service, url);
	}

	public void addEntity(Class<?> entity)
	{
		entityClass.add(entity);
	}

	public void addEJB3Class(Class<?> ejb3Class)
	{
		if (DeployOSGIEJB3Bundle.isEJB3Entity(ejb3Class))
		{
			addEntity(ejb3Class);
		}
		else if (DeployOSGIEJB3Bundle.isEJB3Service(ejb3Class))
		{
			addService(ejb3Class);
		}
		else
		{
			throw new IllegalArgumentException("Class is not a recognized EJB3 class.");
		}
	}

	public void deploy()
	{
		try
		{
			final EJB3BundleUnit bundleUnit = new EJB3BundleUnit(context, baseName);
			final Context bundleContext = context.createSubcontext(baseName);

			deployEntities(bundleUnit);
			deployServices(bundleUnit, bundleContext);
			BundleUnitManager.addBundleUnit(bundleUnit);
			deployed = true;
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void undeploy()
	{
		try
		{
			final EJB3BundleUnit bundleUnit = BundleUnitManager.getBundleUnit(baseName);

			bundleUnit.close();

			undeployEntities(bundleUnit);
			undeployServices(bundleUnit, (Context) context.lookup(baseName));

			context.destroySubcontext(baseName);
			deployed = false;
		}
		catch (NamingException e)
		{
			LOGGER.error(e.getMessage(), e);
		}
	}

	public boolean isDeployed()
	{
		return deployed;
	}

	private void deployEntities(EJB3BundleUnit bundleUnit) throws CoreException, NamingException, IOException, ClassNotFoundException
	{
		if (entityClass.isEmpty())
		{
			return;
		}

		final JPAProvider provider = jpaProviderFactory.create();

		for (Class<?> entity : entityClass)
		{
			provider.addAnnotatedClass(entity);
		}

		persistenceUnitInfoXml.setClassLoader(classLoader);
		provider.configure(persistenceUnitInfoXml);

		EntityManagerFactory buildEntityManagerFactory = provider.buildEntityManagerFactory();

		bundleUnit.setManagerFactory(buildEntityManagerFactory);

		if (persistenceUnitInfoXml.getProperties().containsKey("jboss.entity.manager.jndi.name"))
		{
			context.bind(persistenceUnitInfoXml.getProperties().getProperty("jboss.entity.manager.jndi.name"), new JNDIEntityManager(bundleUnit));
		}
	}

	private void undeployEntities(EJB3BundleUnit bundleUnit) throws NamingException
	{
		bundleUnit.getManagerFactory().close();
		bundleUnit.setManagerFactory(null);

		if (persistenceUnitInfoXml.getProperties().containsKey("jboss.entity.manager.jndi.name"))
		{
			context.unbind(persistenceUnitInfoXml.getProperties().getProperty("jboss.entity.manager.jndi.name"));
		}
	}

	private void deployServices(EJB3BundleUnit bundleUnit, Context bundleContext) throws NamingException
	{
		if (!ejb3Service.isEmpty())
		{
			for (Map.Entry<Class<?>, URL> serviceEntry : ejb3Service.entrySet())
			{
				final String serviceContextKey = baseName + ":" + serviceEntry.getKey().getSimpleName();
				final Context serviceContext = bundleContext.createSubcontext(serviceEntry.getKey().getSimpleName());
				EJb3Service ejb3Service = null;

				if (bindedEjb3Services.containsKey(serviceContextKey))
				{
					ejb3Service = bindedEjb3Services.get(serviceContextKey);
				}
				else
				{
					ejb3Service = new StatelessService(executorService, userTransaction, serviceEntry.getKey(), bundleUnit);
					bindedEjb3Services.put(serviceContextKey, ejb3Service);
				}

				if (DeployOSGIEJB3Bundle.isLocalService(serviceEntry.getKey()))
				{
					serviceContext.rebind("local", ejb3Service);
				}

				if (DeployOSGIEJB3Bundle.isRemoteService(serviceEntry.getKey()))
				{
					serviceContext.rebind("remote", ejb3Service);
					MobPlugin.getRemoteServicesRegistry().registerService(baseName, serviceEntry.getKey().getSimpleName(),
							DeployOSGIEJB3Bundle.getRemoteServiceInterface(serviceEntry.getKey()));
				}

				bundleUnit.addService(ejb3Service);
			}

			// bind interceptors
			Interceptors interceptors = new Interceptors();
			interceptors.addAllServices(bundleUnit.getServicesNames());

			if (ejbJarXml != null)
			{
				Map<String, String> interceptorsByEjbName = ejbJarXml.getInterceptorsByEjbName();
				Set<Entry<String, String>> entrySet = interceptorsByEjbName.entrySet();
				for (Entry<String, String> entry : entrySet)
				{
					try
					{
						interceptors.registerInterceptor(entry.getKey(), Class.forName(entry.getValue(), true, classLoader));
					}
					catch (Exception e)
					{
						LOGGER.error(e.getMessage(), e);
					}
				}
			}

			bundleUnit.setInterceptors(interceptors);

		}
	}

	private void undeployServices(EJB3BundleUnit bundleUnit, Context bundleContext) throws NamingException
	{
		for (Entry<Class<?>, URL> service : ejb3Service.entrySet())
		{
			final String serviceContextKey = baseName + ":" + service.getKey().getSimpleName();
			final Context serviceContext = (Context) bundleContext.lookup(service.getKey().getSimpleName());

			if (DeployOSGIEJB3Bundle.isLocalService(service.getKey()))
			{
				serviceContext.unbind("local");
			}

			if (DeployOSGIEJB3Bundle.isRemoteService(service.getKey()))
			{
				serviceContext.unbind("remote");
				MobPlugin.getRemoteServicesRegistry().unregisterService(baseName, DeployOSGIEJB3Bundle.getRemoteServiceInterface(service.getKey()));
			}

			if (bindedEjb3Services.containsKey(serviceContextKey))
			{
				bundleUnit.removeService(bindedEjb3Services.get(serviceContextKey));
			}

			bundleContext.destroySubcontext(service.getKey().getSimpleName());
		}
	}

	public void setEjbJarXml(EjbJarXml ejbJarXml)
	{
		this.ejbJarXml = ejbJarXml;
	}
}

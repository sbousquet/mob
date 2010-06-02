package com.netappsid.mob.ejb3.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netappsid.mob.ejb3.MobPlugin;
import com.netappsid.mob.ejb3.internal.interceptors.InterceptorHandler;
import com.netappsid.mob.ejb3.internal.interceptors.Interceptors;

/**
 * 
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.10 $
 */
public class EJB3BundleUnit implements EJB3LifecycleManager
{
	private static Logger logger = LoggerFactory.getLogger(EJB3BundleUnit.class);

	private String name;

	private Map<Class, EJb3Service> localService = new HashMap<Class, EJb3Service>();
	private Map<Class, EJb3Service> remoteService = new HashMap<Class, EJb3Service>();
	private Map<Class, EJb3Service> beanService = new HashMap<Class, EJb3Service>();

	private EntityManagerFactory managerFactory;

	private StatelessPool statelessPool = new StatelessPool(this);

	private Interceptors interceptors;

	private static final ThreadLocal<Map<EJB3BundleUnit, EntityManager>> sessionEntityManagers = new ThreadLocal<Map<EJB3BundleUnit, EntityManager>>()
		{
			@Override
			protected java.util.Map<EJB3BundleUnit, EntityManager> initialValue()
			{
				return new IdentityHashMap<EJB3BundleUnit, EntityManager>();
			};
		};

	public EJB3BundleUnit(String name)
	{
		this.name = name;
	}

	public void addService(EJb3Service service)
	{
		if (service.getLocalInterface() != null)
		{
			localService.put(service.getLocalInterface(), service);
		}

		if (service.getRemoteInterface() != null)
		{
			remoteService.put(service.getRemoteInterface(), service);
		}

		if (service.getBeanClass() != null)
		{
			beanService.put(service.getBeanClass(), service);
		}
	}

	/**
	 * @return the statelessPool
	 */
	public StatelessPool getStatelessPool()
	{
		return statelessPool;
	}

	public void removeService(EJb3Service ejb3Service)
	{
		if (ejb3Service.getLocalInterface() != null)
		{
			localService.put(ejb3Service.getLocalInterface(), ejb3Service);
		}

		if (ejb3Service.getRemoteInterface() != null)
		{
			remoteService.put(ejb3Service.getRemoteInterface(), ejb3Service);
		}

		if (ejb3Service.getBeanClass() != null)
		{
			beanService.put(ejb3Service.getBeanClass(), ejb3Service);
		}
	}

	public EntityManager getManager()
	{

		EntityManager manager = sessionEntityManagers.get().get(this);

		try
		{
			if (manager == null || !manager.isOpen())
			{
				manager = managerFactory.createEntityManager();
				sessionEntityManagers.get().put(this, manager);
				manager.joinTransaction();
			}
		}
		catch (IllegalStateException e)
		{
			logger.error(e.getMessage(), e);
		}

		return manager;
	}

	private String getJNDIName(Class<?> serviceClass)
	{
		if (localService.containsKey(serviceClass))
		{
			return name + "/" + localService.get(serviceClass).getName() + "/local";
		}
		else if (remoteService.containsKey(serviceClass))
		{
			return name + "/" + remoteService.get(serviceClass).getName() + "/remote";
		}
		else if (beanService.containsKey(serviceClass))
		{
			return name + "/" + beanService.get(serviceClass).getName() + "/remote";
		}
		else
		{
			return null;
		}
	}

	/**
	 * Check for injection
	 * 
	 * This method is used to dynamically bind different object into the passed object. This is used a lot in J2EE environments since EJB3. Example of objects
	 * injected are entitymanagers, ejbcontext etc.
	 */
	public void inject(Object obj)
	{

		List<Field> fieldList = getDeclaredFields(obj.getClass(), true);

		for (Field field : fieldList)
		{
			Object toInject = null;

			if (field.isAnnotationPresent(EJB.class))
			{
				EJB ejb = field.getAnnotation(EJB.class);
				String jndiName = ejb.mappedName();

				if (jndiName != null && !jndiName.equals(""))
				{
					try
					{
						toInject = MobPlugin.getService(Context.class).lookup(jndiName);
					}
					catch (NamingException e)
					{
						logger.error(e.getMessage(), e);
					}
				}
				else
				{
					toInject = getEJBService(field.getType());
				}

			}
			else if (field.isAnnotationPresent(PersistenceContext.class))
			{
				toInject = new TransactionScopedEntityManager(this);
			}
			else if (field.isAnnotationPresent(PersistenceUnit.class))
			{
				toInject = managerFactory;
			}
			else if (field.isAnnotationPresent(Resource.class))
			{
				// TODO manage @Ressource annotation
			}

			if (toInject != null)
			{
				try
				{
					field.setAccessible(true);
					field.set(obj, toInject);
				}
				catch (Exception e)
				{
					logger.error(e.getMessage(), e);
				}
			}

		}
	}

	/**
	 * @param type
	 * @return
	 */
	private Object getEJBService(Class<?> serviceClass)
	{
		if (localService.containsKey(serviceClass))
		{
			return localService.get(serviceClass).getProxy();
		}
		else if (remoteService.containsKey(serviceClass))
		{
			return remoteService.get(serviceClass).getProxy();
		}
		else if (beanService.containsKey(serviceClass))
		{
			return beanService.get(serviceClass).getProxy();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Retrieve the fields contains in a class (and it's superclass if checkRecursively is true)
	 * 
	 * @param inClass
	 *            the class to retrieve the fields from
	 * @param checkRecursively
	 *            true if the fields of the superclasses must be retrieve too
	 * @return A list of fields for the given class.
	 */
	private static List<Field> getDeclaredFields(Class<?> inClass, boolean checkRecursively)
	{
		List<Field> fields = new ArrayList<Field>();
		getDeclaredFields(inClass, fields, checkRecursively);
		return fields;
	}

	/**
	 * Populate the list of fields for the given class. Go recursively in the superclasses if needed
	 * 
	 * @param inClass
	 *            the class to retrieve the fields from
	 * @param list
	 *            the list to populate
	 * @param checkRecursively
	 *            true if the fields of the superclasses must be retrieve too
	 */
	private static void getDeclaredFields(Class<?> inClass, List<Field> list, boolean checkRecursively)
	{
		list.addAll(Arrays.asList(inClass.getDeclaredFields()));
		if (checkRecursively && inClass.getSuperclass() != null)
		{
			getDeclaredFields(inClass.getSuperclass(), list, checkRecursively);
		}
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Returns the managerFactory.
	 */
	public EntityManagerFactory getManagerFactory()
	{
		return managerFactory;
	}

	/**
	 * @param managerFactory
	 *            The managerFactory to set.
	 */
	public void setManagerFactory(EntityManagerFactory managerFactory)
	{
		this.managerFactory = managerFactory;
	}

	public void close()
	{

		Map<EJB3BundleUnit, EntityManager> map = sessionEntityManagers.get();

		Collection<EntityManager> values = map.values();

		for (EntityManager entityManager : values)
		{
			entityManager.close();
		}

		map.clear();

	}

	public void flush()
	{
		Collection<EntityManager> values = sessionEntityManagers.get().values();
		for (EntityManager entityManager : values)
		{
			entityManager.flush();
		}
	}

	public List<String> getServicesNames()
	{
		List<String> names = new ArrayList<String>();

		for (Class<?> clazz : beanService.keySet())
		{
			names.add(clazz.getSimpleName());
		}
		return names;
	}

	public List<InterceptorHandler> getInterceptors(String serviceName)
	{
		if (interceptors != null)
		{
			return interceptors.getInterceptors(serviceName);
		}
		return null;
	}

	public void setInterceptors(Interceptors interceptors)
	{
		this.interceptors = interceptors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netappsid.mod.ejb3.internal.EJB3LifecycleManager#create(java.lang.Class)
	 */
	@Override
	public <T> T create(Class<T> toCreate) throws InstantiationException, IllegalAccessException
	{
		T newInstance = toCreate.newInstance();
		inject(newInstance);
		return newInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netappsid.mod.ejb3.internal.EJB3LifecycleManager#destroy(java.lang.Object)
	 */
	@Override
	public void destroy(Object toDestroy)
	{}
}

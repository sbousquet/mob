/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class StatelessPool
{

	private Map<Class<?>, Pool<?>> pools = new ConcurrentHashMap<Class<?>, Pool<?>>();
	private final EJB3LifecycleManager lifecycleManager;

	/**
	 * @param ejb3BundleUnit
	 */
	public StatelessPool(EJB3LifecycleManager lifecycleManager)
	{
		this.lifecycleManager = lifecycleManager;
	}

	/**
	 * @param class1
	 */
	private void registerStatelessBean(Class<?> clazz)
	{
		pools.put(clazz, new Pool(clazz,lifecycleManager));
	}

	public boolean isRegister(Class<?> beanClass)
	{
		return pools.containsKey(beanClass);
	}

	/**
	 * @param class1
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InterruptedException
	 */
	public <T> T get(Class<T> beanClass) throws InstantiationException, IllegalAccessException, InterruptedException
	{
		if (!isRegister(beanClass))
		{
			registerStatelessBean(beanClass);
		}

		Pool<T> pool = (Pool<T>) pools.get(beanClass);

		return pool.get();
	}

	/**
	 * @param testServiceBean
	 */
	public <T> void recycle(T toRecycle)
	{
		if (isRegister(toRecycle.getClass()))
		{
			Pool<T> pool = (Pool<T>) pools.get(toRecycle.getClass());
			pool.recycle(toRecycle);
		}
	}

	/**
	 * @param class1
	 * @return
	 */
	public <T> Pool<T> getPool(Class<T> beanclass)
	{
		return (Pool<T>) pools.get(beanclass);
	}

}

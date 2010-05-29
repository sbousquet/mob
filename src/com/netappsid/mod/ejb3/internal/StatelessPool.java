/**
 * 
 */
package com.netappsid.mod.ejb3.internal;

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

	/**
	 * @param class1
	 */
	private void registerStatelessBean(Class<?> clazz)
	{
		pools.put(clazz, new Pool(clazz));
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

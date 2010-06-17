/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class Pool<T>
{

	private AtomicInteger sizeLimit = new AtomicInteger(100);

	private final Class<T> beanClass;

	private ArrayBlockingQueue<T> availables = new ArrayBlockingQueue<T>(sizeLimit.get(), false);

	private AtomicInteger count = new AtomicInteger(0);

	private final EJB3LifecycleManager lifecycleManager;

	/**
	 * @param clazz
	 * @param lifecycleManager 
	 */
	public Pool(Class<T> clazz, EJB3LifecycleManager lifecycleManager)
	{
		this.beanClass = clazz;
		this.lifecycleManager = lifecycleManager;
	}

	/**
	 * @return
	 * @throws Exception 
	 */
	public T get() throws Exception
	{
		T instance = availables.poll();

		if (instance != null)
		{
			return instance;
		}

		int created = count.getAndIncrement();

		if (created < sizeLimit.get())
		{
			return lifecycleManager.create(beanClass);
		}
		// the limit is reach
		else
		{
			count.decrementAndGet();
			
			//wait it become available
			return availables.take();
		}

	}

	public void recycle(T toRecycle) throws Exception
	{
		if (!availables.offer(toRecycle))
		{
			lifecycleManager.destroy(toRecycle);
		}
	}

	/**
	 * @param i
	 */
	public void setLimit(int i)
	{
		sizeLimit.set(i);
	}
}

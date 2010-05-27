/**
 * 
 */
package com.netappsid.mod.ejb3;

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

	/**
	 * @param clazz
	 */
	public Pool(Class<T> clazz)
	{
		this.beanClass = clazz;
	}

	/**
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InterruptedException 
	 */
	public T get() throws InstantiationException, IllegalAccessException, InterruptedException
	{
		T instance = availables.poll();

		if (instance != null)
		{
			return instance;
		}

		int created = count.getAndIncrement();

		if (created < sizeLimit.get())
		{
			return beanClass.newInstance();
		}
		// the limit is reach
		else
		{
			count.decrementAndGet();
			
			//wait it become available
			return availables.take();
		}

	}

	public void recycle(T toRecycle)
	{
		if (!availables.offer(toRecycle))
		{
			destroy(toRecycle);
		}
	}

	/**
	 * @param toRecycle
	 */
	private void destroy(T toRecycle)
	{
		//TODO implement detroy
	}

	/**
	 * @param i
	 */
	public void setLimit(int i)
	{
		sizeLimit.set(i);
	}
}

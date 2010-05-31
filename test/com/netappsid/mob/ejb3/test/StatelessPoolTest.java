/**
 * 
 */
package com.netappsid.mob.ejb3.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Test;

import com.netappsid.mob.ejb3.internal.Pool;
import com.netappsid.mob.ejb3.internal.StatelessPool;
import com.netappsid.mob.ejb3.test.beans.TestServiceBean;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class StatelessPoolTest
{

	@Test
	public void testRegisterStateless() throws InstantiationException, IllegalAccessException, InterruptedException
	{
		StatelessPool statelessPool = new StatelessPool(new DummyLifecycleManager());
		statelessPool.get(TestServiceBean.class);

		assertTrue(statelessPool.isRegister(TestServiceBean.class));
	}

	@Test
	public void testCreateStatelessInstance() throws InstantiationException, IllegalAccessException, InterruptedException
	{
		StatelessPool statelessPool = new StatelessPool(new DummyLifecycleManager());

		TestServiceBean testServiceBean = statelessPool.get(TestServiceBean.class);

		assertNotNull(testServiceBean);
	}

	@Test
	public void testReuseStatelessInstance() throws InstantiationException, IllegalAccessException, InterruptedException
	{
		StatelessPool statelessPool = new StatelessPool(new DummyLifecycleManager());

		TestServiceBean testServiceBean = statelessPool.get(TestServiceBean.class);

		statelessPool.recycle(testServiceBean);

		TestServiceBean testServiceBean2 = statelessPool.get(TestServiceBean.class);

		assertSame(testServiceBean, testServiceBean2);

	}

	@Test
	public void testLimitReach() throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException
	{
		StatelessPool statelessPool = new StatelessPool(new DummyLifecycleManager());
		TestServiceBean testServiceBean2 = statelessPool.get(TestServiceBean.class);
		statelessPool.recycle(testServiceBean2);

		final Pool<TestServiceBean> pool = statelessPool.getPool(TestServiceBean.class);
		pool.setLimit(1);

		TestServiceBean testServiceBean = pool.get();

		Callable<TestServiceBean> callable = new Callable<TestServiceBean>()
			{
				/*
				 * (non-Javadoc)
				 * 
				 * @see java.util.concurrent.Callable#call()
				 */
				@Override
				public TestServiceBean call() throws Exception
				{
					return pool.get();
				}
			};

		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();

		Future<TestServiceBean> submit = newSingleThreadExecutor.submit(callable);

		assertFalse(submit.isDone());

		pool.recycle(testServiceBean);

		assertSame(testServiceBean, submit.get());

	}

}

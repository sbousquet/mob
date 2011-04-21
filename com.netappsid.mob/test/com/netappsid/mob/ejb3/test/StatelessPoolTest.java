package com.netappsid.mob.ejb3.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import com.netappsid.mob.ejb3.internal.EJB3BundleUnit;
import com.netappsid.mob.ejb3.internal.Pool;
import com.netappsid.mob.ejb3.internal.StatelessPool;
import com.netappsid.mob.ejb3.test.beans.TestServiceBean;

public class StatelessPoolTest
{
	private StatelessPool statelessPool;
	private EJB3BundleUnit bundleUnit;

	@Before
	public void before() throws Exception
	{
		bundleUnit = mock(EJB3BundleUnit.class);
		statelessPool = new StatelessPool(bundleUnit);
		
		when(bundleUnit.create(TestServiceBean.class)).thenReturn(new TestServiceBean());
	}
	
	@Test
	public void testRegisterStateless() throws Exception
	{
		statelessPool.get(TestServiceBean.class);

		assertTrue(statelessPool.isRegister(TestServiceBean.class));
	}

	@Test
	public void testCreateStatelessInstance() throws Exception
	{
		assertNotNull(statelessPool.get(TestServiceBean.class));
	}

	@Test
	public void testReuseStatelessInstance() throws Exception
	{
		TestServiceBean testServiceBean = statelessPool.get(TestServiceBean.class);

		statelessPool.recycle(testServiceBean);

		TestServiceBean testServiceBean2 = statelessPool.get(TestServiceBean.class);

		assertSame(testServiceBean, testServiceBean2);

	}

	@Test
	public void testLimitReach() throws Exception
	{
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

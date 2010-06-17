/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import javax.naming.NamingException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.junit.Before;
import org.junit.Test;

import com.netappsid.mob.ejb3.internal.EJB3BundleUnit;
import com.netappsid.mob.ejb3.internal.InvocationHandler;
import com.netappsid.mob.ejb3.internal.FakeRemoteEJBServiceLink;

import static org.junit.Assert.*;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class InvocationHandlerTest
{
	private Boolean setRollback;

	@Before
	public void reset()
	{
		setRollback = null;
	}

	@Test
	public void testNoRollbackWhenExceptionThrow() throws Exception
	{

		TestServiceBean testServiceBean = new TestServiceBean();
		InvocationHandler invocationHandler = new InvocationHandler(new FakeRemoteEJBServiceLink(), new EJB3BundleUnit("test"), testServiceBean,
				TestServiceBean.class.getMethod("testMethodThrowException"), null)
			{
				/*
				 * (non-Javadoc)
				 * 
				 * @see com.netappsid.ejb3.internal.InvocationHandler#getUserTransaction()
				 */
				@Override
				protected UserTransaction getUserTransaction() throws NamingException
				{
					return new UserTransactionAdapter()
						{
							/*
							 * (non-Javadoc)
							 * 
							 * @see com.netappsid.ejb3.internal.UserTransactionAdapter#rollback()
							 */
							@Override
							public void rollback() throws IllegalStateException, SecurityException, SystemException
							{
								setRollback = true;
							}

							/*
							 * (non-Javadoc)
							 * 
							 * @see com.netappsid.ejb3.internal.UserTransactionAdapter#getStatus()
							 */
							@Override
							public int getStatus() throws SystemException
							{
								return Status.STATUS_ACTIVE;
							}
						};
				}
			};

		// will throw exception
		try
		{
			invocationHandler.call();
		}
		catch (Exception e)
		{}

		assertNull(setRollback);
	}

	@Test
	public void testRollbackWhenExceptionThrow() throws Exception
	{

		TestServiceBean testServiceBean = new TestServiceBean();
		InvocationHandler invocationHandler = new InvocationHandler(new FakeRemoteEJBServiceLink(), new EJB3BundleUnit("test"), testServiceBean,
				TestServiceBean.class.getMethod("testMethodThrowException"), new Object[0])
			{
				/*
				 * (non-Javadoc)
				 * 
				 * @see com.netappsid.ejb3.internal.InvocationHandler#getUserTransaction()
				 */
				@Override
				protected UserTransaction getUserTransaction() throws NamingException
				{
					return new UserTransactionAdapter()
						{

							/*
							 * (non-Javadoc)
							 * 
							 * @see com.netappsid.ejb3.internal.UserTransactionAdapter#rollback()
							 */
							@Override
							public void rollback() throws IllegalStateException, SecurityException, SystemException
							{
								setRollback = true;
							}

							/*
							 * (non-Javadoc)
							 * 
							 * @see com.netappsid.ejb3.internal.UserTransactionAdapter#getStatus()
							 */
							@Override
							public int getStatus() throws SystemException
							{
								return Status.STATUS_NO_TRANSACTION;
							}
						};
				}
			};

		// will throw exception
		try
		{
			invocationHandler.call();
		}
		catch (Exception e)
		{}

		assertTrue(setRollback);
	}
}

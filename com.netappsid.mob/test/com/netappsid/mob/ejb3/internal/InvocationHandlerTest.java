package com.netappsid.mob.ejb3.internal;

import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.junit.Test;

import com.netappsid.mob.ejb3.EJBServiceLink;

public class InvocationHandlerTest
{
	@Test
	public void testRollbackWhenExceptionThrow() throws Exception
	{
		final UserTransaction userTransaction = mock(UserTransaction.class);
		when(userTransaction.getStatus()).thenReturn(Status.STATUS_NO_TRANSACTION);

		final EJBServiceLink ejbServiceLink = mock(EJBServiceLink.class);
		final EJB3BundleUnit ejb3BundleUnit = mock(EJB3BundleUnit.class);
		final TestServiceBean serviceBean = new TestServiceBean();
		final Method invokedMethod = TestServiceBean.class.getMethod("testMethodThrowException");

		final InvocationHandler invocationHandler = new InvocationHandler(ejbServiceLink, userTransaction, ejb3BundleUnit, serviceBean, invokedMethod, null);

		try
		{
			invocationHandler.call();
		}
		catch (Exception e)
		{

		}

		verify(userTransaction).rollback();
	}
}

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

import com.netappsid.mob.ejb3.EJBServiceLink;
import com.netappsid.mob.ejb3.internal.EJB3BundleUnit;
import com.netappsid.mob.ejb3.internal.InvocationHandler;
import com.netappsid.mob.ejb3.internal.FakeRemoteEJBServiceLink;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class InvocationHandlerTest
{


	@Test
	public void testRollbackWhenExceptionThrow() throws Exception
	{

		TestServiceBean testServiceBean = new TestServiceBean();

		UserTransaction userTransaction = mock(UserTransaction.class);
		InvocationHandler invocationHandler = new InvocationHandler(mock(EJBServiceLink.class), userTransaction, mock(EJB3BundleUnit.class), testServiceBean, TestServiceBean.class.getMethod("testMethodThrowException"), null);
		
		// will throw exception
		try
		{
			invocationHandler.call();
		}
		catch (Exception e)
		{}

		verify(userTransaction).setRollbackOnly();
	}
}

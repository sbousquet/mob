/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.Context;
import javax.transaction.UserTransaction;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.service.packageadmin.PackageAdmin;

import com.netappsid.mob.ejb3.internal.EJB3BundleUnit;
import com.netappsid.mob.ejb3.internal.StatelessService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class EJB3BundleUnitTest
{

	private static ExecutorService newSingleThreadExecutor;

	@BeforeClass
	public static void beforeClass()
	{
		newSingleThreadExecutor = Executors.newSingleThreadExecutor();
	}

	@AfterClass
	public static void afterClass()
	{
		newSingleThreadExecutor.shutdown();
	}

	@Test
	public void testInjectStateless() throws Exception
	{
		EJB3BundleUnit ejb3BundleUnit = new EJB3BundleUnit(mock(Context.class), "test");
		UserTransaction userTransaction = mock(UserTransaction.class);
		PackageAdmin packageAdmin = mock(PackageAdmin.class);
		ejb3BundleUnit.addService(new StatelessService(newSingleThreadExecutor,new FakeRemoteEJBServiceLink(packageAdmin), userTransaction, TestServiceBean.class, ejb3BundleUnit));
		ejb3BundleUnit.addService(new StatelessService(newSingleThreadExecutor,new FakeRemoteEJBServiceLink(packageAdmin), userTransaction, TestInjectServiceBean.class, ejb3BundleUnit));

		TestInjectServiceBean create = ejb3BundleUnit.create(TestInjectServiceBean.class);
		assertTrue(create.isInject());
	}
}

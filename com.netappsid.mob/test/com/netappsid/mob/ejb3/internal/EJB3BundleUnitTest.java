/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.Context;
import javax.transaction.UserTransaction;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.service.packageadmin.PackageAdmin;

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
		EJB3BundleUnit ejb3BundleUnit = getEjb3BundleUnit();
		TestInjectServiceBean create = ejb3BundleUnit.create(TestInjectServiceBean.class);
		assertTrue(create.isInjectEJB());
	}

	@Test
	public void testInjectSessionContext() throws Exception
	{
		EJB3BundleUnit ejb3BundleUnit = getEjb3BundleUnit();
		TestInjectServiceBean create = ejb3BundleUnit.create(TestInjectServiceBean.class);
		assertTrue(create.isInjectSessionContext());
	}

	private EJB3BundleUnit getEjb3BundleUnit()
	{
		EJB3BundleUnit ejb3BundleUnit = new EJB3BundleUnit(Thread.currentThread().getContextClassLoader(),mock(Context.class), mock(UserTransaction.class), "test");
		UserTransaction userTransaction = mock(UserTransaction.class);
		PackageAdmin packageAdmin = mock(PackageAdmin.class);
		ejb3BundleUnit.addService(new StatelessService(newSingleThreadExecutor, new FakeRemoteEJBServiceLink(packageAdmin), userTransaction,
				TestServiceBean.class, ejb3BundleUnit));
		ejb3BundleUnit.addService(new StatelessService(newSingleThreadExecutor, new FakeRemoteEJBServiceLink(packageAdmin), userTransaction,
				TestInjectServiceBean.class, ejb3BundleUnit));
		return ejb3BundleUnit;
	}
}

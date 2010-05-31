package com.netappsid.mob.ejb3.test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.concurrent.ExecutorService;

import javassist.util.proxy.ProxyObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.netappsid.mob.ejb3.internal.EJB3BundleUnit;
import com.netappsid.mob.ejb3.internal.StatelessService;
import com.netappsid.mob.ejb3.osgi.DeployOSGIEJB3Bundle;
import com.netappsid.mob.ejb3.test.beans.TestServiceBean;
import com.netappsid.mob.ejb3.test.beans.TestServiceRemote;

public class StatelessTest
{

	private static ExecutorService executorService;

	@BeforeClass
	public static void setUp()
	{
		DeployOSGIEJB3Bundle.init();
		executorService = DeployOSGIEJB3Bundle.getExecutorService();
	}

	@AfterClass
	public static void after()
	{
		executorService.shutdown();
	}

	@Test
	public void testStatelessDeploy()
	{
		EJB3BundleUnit ejb3BundleUnit = new EJB3BundleUnit("test");
		StatelessService statelessService = new StatelessService(executorService, TestServiceBean.class, ejb3BundleUnit);
		ejb3BundleUnit.addService(statelessService);

		Object content = statelessService.getContent();

		assertTrue(content instanceof ProxyObject);

	}

	@Test
	public void testStatelessInstanceMustChange()
	{
		EJB3BundleUnit ejb3BundleUnit = new EJB3BundleUnit("test");
		StatelessService statelessService = new StatelessService(executorService, TestServiceBean.class, ejb3BundleUnit);
		ejb3BundleUnit.addService(statelessService);

		final TestServiceRemote content = (TestServiceRemote) statelessService.getContent();

		
		Thread thread = new Thread()
		{
			public void run() 
			{
				content.testSleep();
			}
		};

		thread.start();
		
		IdentityTest testIdentity = content.testIdentity(new IdentityTest());
		
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		IdentityTest testIdentity2 = content.testIdentity(new IdentityTest());

		assertFalse(testIdentity.getIdentity() == testIdentity2.getIdentity());

	}

}

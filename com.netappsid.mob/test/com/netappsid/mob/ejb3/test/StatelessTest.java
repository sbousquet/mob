package com.netappsid.mob.ejb3.test;

import static junit.framework.Assert.assertTrue;

import java.util.concurrent.ExecutorService;

import javassist.util.proxy.ProxyObject;

import org.junit.AfterClass;
import org.junit.Assert;
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
	public static void afterClass()
	{
		if (executorService != null)
		{
			executorService.shutdown();
		}
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
	public void testStatelessPostConstruct()
	{
		EJB3BundleUnit ejb3BundleUnit = new EJB3BundleUnit("test");
		StatelessService statelessService = new StatelessService(executorService, TestServiceBean.class, ejb3BundleUnit);
		ejb3BundleUnit.addService(statelessService);

		Object content = statelessService.getContent();

		Assert.assertEquals("Test must set", ((TestServiceRemote) content).getPostConstruct());
	}

}

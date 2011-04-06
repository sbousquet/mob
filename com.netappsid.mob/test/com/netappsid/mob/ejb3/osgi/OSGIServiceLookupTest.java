package com.netappsid.mob.ejb3.osgi;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;

public class OSGIServiceLookupTest
{
	private static final String SERVICE_NAME = String.class.getName();

	private OSGIServiceLookup<String> serviceLookup;
	private BundleContext context;
	private ServiceReference serviceReference;

	@Before
	public void before()
	{
		context = mock(BundleContext.class);
		serviceReference = mock(ServiceReference.class);
		serviceLookup = new OSGIServiceLookup<String>(context, String.class);

		when(context.getService(serviceReference)).thenReturn("service");
	}

	@Test
	public void test_retrievesServiceWhenPresent()
	{
		when(context.getServiceReference(SERVICE_NAME)).thenReturn(serviceReference);
		assertEquals("service", serviceLookup.getService());
	}

	@Test
	public void test_retrievesServiceAfterRegisteringListenerThenPresent()
	{
		when(context.getServiceReference(SERVICE_NAME)).thenReturn(null).thenReturn(serviceReference);
		assertEquals("service", serviceLookup.getService());
	}

	@Test
	public void test_retrievesServiceWhenServiceEventIsTriggered()
	{
		when(context.getServiceReference(SERVICE_NAME)).thenReturn(null).thenReturn(null).thenReturn(serviceReference);
		when(serviceReference.isAssignableTo(any(Bundle.class), eq(SERVICE_NAME))).thenReturn(true);

		new Thread()
			{
				@Override
				public void run()
				{
					serviceLookup.serviceChanged(new ServiceEvent(0, serviceReference));
				}
			}.start();

		assertEquals("service", serviceLookup.getService());
	}
}

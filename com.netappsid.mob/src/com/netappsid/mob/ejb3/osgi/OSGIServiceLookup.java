package com.netappsid.mob.ejb3.osgi;

import java.util.concurrent.Exchanger;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSGIServiceLookup<T> implements ServiceListener
{
	private static Logger logger = LoggerFactory.getLogger(OSGIServiceLookup.class);

	private final BundleContext context;
	private final Class<? extends T> service;
	private final Exchanger<ServiceReference> serviceReferenceExchanger;

	public OSGIServiceLookup(BundleContext context, Class<? extends T> service)
	{
		this.context = context;
		this.service = service;
		this.serviceReferenceExchanger = new Exchanger<ServiceReference>();
	}

	OSGIServiceLookup(BundleContext context, Class<? extends T> service, Exchanger<ServiceReference> serviceReferenceExchanger)
	{
		this.context = context;
		this.service = service;
		this.serviceReferenceExchanger = serviceReferenceExchanger;
	}

	public T getService()
	{
		ServiceReference serviceReference = context.getServiceReference(service.getName());

		if (serviceReference == null)
		{
			context.addServiceListener(this);
			serviceReference = context.getServiceReference(service.getName());

			if (serviceReference != null)
			{
				context.removeServiceListener(this);
				return (T) context.getService(serviceReference);
			}

			try
			{
				serviceReference = serviceReferenceExchanger.exchange(null);
			}
			catch (InterruptedException e)
			{
				logger.error(e.getMessage(), e);
			}

			context.removeServiceListener(this);
		}

		return (T) context.getService(serviceReference);
	}

	@Override
	public void serviceChanged(ServiceEvent event)
	{
		if (event.getServiceReference().equals(context.getServiceReference(service.getName())))
		{
			try
			{
				serviceReferenceExchanger.exchange(event.getServiceReference());
			}
			catch (InterruptedException e)
			{
				logger.error(e.getMessage(), e);
			}
		}
	}
}

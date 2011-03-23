package com.netappsid.mob.ejb3.osgi;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSGIServiceLookup implements ServiceListener
{
	private static Logger logger = LoggerFactory.getLogger(OSGIServiceLookup.class);

	private final BundleContext context;
	private final Object wait = new Object();

	public OSGIServiceLookup(BundleContext context)
	{
		this.context = context;
		context.addServiceListener(this);
	}

	public <T> T getService(Class<T> seviceInterface)
	{
		ServiceReference serviceReference = context.getServiceReference(seviceInterface.getName());

		if (serviceReference == null)
		{
			while (serviceReference == null)
			{
				synchronized (wait)
				{
					try
					{
						wait.wait(1000);
					}
					catch (InterruptedException e)
					{
						logger.error(e.getMessage(), e);
					}
				}

				serviceReference = context.getServiceReference(seviceInterface.getName());
			}
		}

		return (T) context.getService(serviceReference);
	}

	@Override
	public void serviceChanged(ServiceEvent event)
	{
		wait.notifyAll();
	}

}

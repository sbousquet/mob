package com.netappsid.mob.ejb3.osgi;

import org.osgi.framework.BundleContext;

public class OSGIServiceLookup
{

	private final BundleContext context;

	public OSGIServiceLookup(BundleContext context)
	{
		this.context = context;
	}
	
	public <T> T getService(Class<T> seviceInterface)
	{
		return (T) context.getService(context.getServiceReference(seviceInterface.getName()));
	}

}

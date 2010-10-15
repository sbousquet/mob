/**
 * 
 */
package com.netappsid.mob.ejb3.test;

import java.io.File;
import java.io.InputStream;
import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class BundleContextAdapter implements BundleContext
{

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#getProperty(java.lang.String)
	 */
	
	public String getProperty(String key)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#getBundle()
	 */
	
	public Bundle getBundle()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#installBundle(java.lang.String, java.io.InputStream)
	 */
	
	public Bundle installBundle(String location, InputStream input) throws BundleException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#installBundle(java.lang.String)
	 */
	
	public Bundle installBundle(String location) throws BundleException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#getBundle(long)
	 */
	
	public Bundle getBundle(long id)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#getBundles()
	 */
	
	public Bundle[] getBundles()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#addServiceListener(org.osgi.framework.ServiceListener, java.lang.String)
	 */
	
	public void addServiceListener(ServiceListener listener, String filter) throws InvalidSyntaxException
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#addServiceListener(org.osgi.framework.ServiceListener)
	 */
	
	public void addServiceListener(ServiceListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#removeServiceListener(org.osgi.framework.ServiceListener)
	 */
	
	public void removeServiceListener(ServiceListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#addBundleListener(org.osgi.framework.BundleListener)
	 */
	
	public void addBundleListener(BundleListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#removeBundleListener(org.osgi.framework.BundleListener)
	 */
	
	public void removeBundleListener(BundleListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#addFrameworkListener(org.osgi.framework.FrameworkListener)
	 */
	
	public void addFrameworkListener(FrameworkListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#removeFrameworkListener(org.osgi.framework.FrameworkListener)
	 */
	
	public void removeFrameworkListener(FrameworkListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#registerService(java.lang.String[], java.lang.Object, java.util.Dictionary)
	 */
	
	public ServiceRegistration registerService(String[] clazzes, Object service, Dictionary properties)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#registerService(java.lang.String, java.lang.Object, java.util.Dictionary)
	 */
	
	public ServiceRegistration registerService(String clazz, Object service, Dictionary properties)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#getServiceReferences(java.lang.String, java.lang.String)
	 */
	
	public ServiceReference[] getServiceReferences(String clazz, String filter) throws InvalidSyntaxException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#getAllServiceReferences(java.lang.String, java.lang.String)
	 */
	
	public ServiceReference[] getAllServiceReferences(String clazz, String filter) throws InvalidSyntaxException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#getServiceReference(java.lang.String)
	 */
	
	public ServiceReference getServiceReference(String clazz)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#getService(org.osgi.framework.ServiceReference)
	 */
	
	public Object getService(ServiceReference reference)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#ungetService(org.osgi.framework.ServiceReference)
	 */
	
	public boolean ungetService(ServiceReference reference)
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#getDataFile(java.lang.String)
	 */
	
	public File getDataFile(String filename)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleContext#createFilter(java.lang.String)
	 */
	
	public Filter createFilter(String filter) throws InvalidSyntaxException
	{
		return null;
	}

}

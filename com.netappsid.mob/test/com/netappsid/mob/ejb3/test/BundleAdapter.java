/**
 * 
 */
package com.netappsid.mob.ejb3.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class BundleAdapter implements Bundle
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getState()
	 */

	@Override
	public int getState()
	{
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#start(int)
	 */

	@Override
	public void start(int options) throws BundleException
	{}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#start()
	 */

	@Override
	public void start() throws BundleException
	{}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#stop(int)
	 */

	@Override
	public void stop(int options) throws BundleException
	{}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#stop()
	 */

	@Override
	public void stop() throws BundleException
	{}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#update(java.io.InputStream)
	 */

	@Override
	public void update(InputStream input) throws BundleException
	{}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#update()
	 */

	@Override
	public void update() throws BundleException
	{}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#uninstall()
	 */

	@Override
	public void uninstall() throws BundleException
	{}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getHeaders()
	 */

	@Override
	public Dictionary getHeaders()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getBundleId()
	 */

	@Override
	public long getBundleId()
	{
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getLocation()
	 */

	@Override
	public String getLocation()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getRegisteredServices()
	 */

	@Override
	public ServiceReference[] getRegisteredServices()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getServicesInUse()
	 */

	@Override
	public ServiceReference[] getServicesInUse()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#hasPermission(java.lang.Object)
	 */

	@Override
	public boolean hasPermission(Object permission)
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getResource(java.lang.String)
	 */

	@Override
	public URL getResource(String name)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getHeaders(java.lang.String)
	 */

	@Override
	public Dictionary getHeaders(String locale)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getSymbolicName()
	 */

	@Override
	public String getSymbolicName()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#loadClass(java.lang.String)
	 */

	@Override
	public Class loadClass(String name) throws ClassNotFoundException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getResources(java.lang.String)
	 */

	@Override
	public Enumeration getResources(String name) throws IOException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getEntryPaths(java.lang.String)
	 */

	@Override
	public Enumeration getEntryPaths(String path)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getEntry(java.lang.String)
	 */

	@Override
	public URL getEntry(String path)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getLastModified()
	 */

	@Override
	public long getLastModified()
	{
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#findEntries(java.lang.String, java.lang.String, boolean)
	 */

	@Override
	public Enumeration findEntries(String path, String filePattern, boolean recurse)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getBundleContext()
	 */

	@Override
	public BundleContext getBundleContext()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getSignerCertificates(int)
	 */

	@Override
	public Map getSignerCertificates(int signersType)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getVersion()
	 */
	@Override
	public Version getVersion()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Bundle o)
	{
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#adapt(java.lang.Class)
	 */
	@Override
	public <A> A adapt(Class<A> type)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.Bundle#getDataFile(java.lang.String)
	 */
	@Override
	public File getDataFile(String filename)
	{
		return null;
	}

}

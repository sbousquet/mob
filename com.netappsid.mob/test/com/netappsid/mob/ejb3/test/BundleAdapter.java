/**
 * 
 */
package com.netappsid.mob.ejb3.test;

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

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getState()
	 */
	
	public int getState()
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#start(int)
	 */
	
	public void start(int options) throws BundleException
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#start()
	 */
	
	public void start() throws BundleException
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#stop(int)
	 */
	
	public void stop(int options) throws BundleException
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#stop()
	 */
	
	public void stop() throws BundleException
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#update(java.io.InputStream)
	 */
	
	public void update(InputStream input) throws BundleException
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#update()
	 */
	
	public void update() throws BundleException
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#uninstall()
	 */
	
	public void uninstall() throws BundleException
	{}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getHeaders()
	 */
	
	public Dictionary getHeaders()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getBundleId()
	 */
	
	public long getBundleId()
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getLocation()
	 */
	
	public String getLocation()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getRegisteredServices()
	 */
	
	public ServiceReference[] getRegisteredServices()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getServicesInUse()
	 */
	
	public ServiceReference[] getServicesInUse()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#hasPermission(java.lang.Object)
	 */
	
	public boolean hasPermission(Object permission)
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getResource(java.lang.String)
	 */
	
	public URL getResource(String name)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getHeaders(java.lang.String)
	 */
	
	public Dictionary getHeaders(String locale)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getSymbolicName()
	 */
	
	public String getSymbolicName()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#loadClass(java.lang.String)
	 */
	
	public Class loadClass(String name) throws ClassNotFoundException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getResources(java.lang.String)
	 */
	
	public Enumeration getResources(String name) throws IOException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getEntryPaths(java.lang.String)
	 */
	
	public Enumeration getEntryPaths(String path)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getEntry(java.lang.String)
	 */
	
	public URL getEntry(String path)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getLastModified()
	 */
	
	public long getLastModified()
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#findEntries(java.lang.String, java.lang.String, boolean)
	 */
	
	public Enumeration findEntries(String path, String filePattern, boolean recurse)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getBundleContext()
	 */
	
	public BundleContext getBundleContext()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getSignerCertificates(int)
	 */
	
	public Map getSignerCertificates(int signersType)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.Bundle#getVersion()
	 */
	public Version getVersion()
	{
		return null;
	}

}

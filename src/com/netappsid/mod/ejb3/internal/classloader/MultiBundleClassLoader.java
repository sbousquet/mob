/**
 * 
 */
package com.netappsid.mod.ejb3.internal.classloader;

import java.util.Collection;
import java.util.Iterator;

import org.osgi.framework.Bundle;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class MultiBundleClassLoader extends ClassLoader
{
	private Collection<Bundle> bundles;

	public MultiBundleClassLoader(Collection<Bundle> bundles)
	{
		this.bundles = bundles;
	}

	public Collection<Bundle> getBundles()
	{
		return bundles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException
	{
		Iterator<Bundle> iterator = bundles.iterator();
		Class<?> found = null;
		while (iterator.hasNext())
		{
			Bundle bundle = (Bundle) iterator.next();

			try
			{
				found = bundle.loadClass(name);
			}
			catch (Exception e)
			{
				//do nothing we must pass every bundle
			}

			if (found != null)
			{
				break;
			}
		}
		
		if(found!=null)
		{
			return found;
		}
		else
		{
			throw new ClassNotFoundException(name);
		}
	}

}

/**
 * 
 */
package com.netappsid.mob.ejb3.internal.classloader;

import java.util.Collection;
import java.util.Iterator;

import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;

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
	private final PackageAdmin packageAdmin;

	public MultiBundleClassLoader(PackageAdmin packageAdmin, Collection<Bundle> bundles)
	{
		this.packageAdmin = packageAdmin;
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
		try
		{
			return MultiBundleClassLoader.class.getClassLoader().loadClass(name);
		} catch (ClassNotFoundException e1)
		{
		}

		Iterator<Bundle> iterator = bundles.iterator();
		Class<?> found = null;
		while (iterator.hasNext())
		{
			Bundle bundle = (Bundle) iterator.next();

			try
			{
				found = bundle.loadClass(name);
			} catch (Exception e)
			{
				// do nothing we must pass every bundle
			}

			if (found != null)
			{
				return found;
			}
		}

		ExportedPackage exportedPackage = packageAdmin.getExportedPackage(name.substring(0, name.lastIndexOf('.')));

		if (exportedPackage != null)
		{
			return exportedPackage.getExportingBundle().loadClass(name);
		}

		throw new ClassNotFoundException(name);
	}

}

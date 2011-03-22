/**
 * 
 */
package com.netappsid.mob.ejb3.io;

import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.PackageAdmin;

import com.netappsid.mob.ejb3.internal.classloader.OSGIClassLoader;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class OSGIClassResolver implements IMultiVersionClassResolver
{

	private final PackageAdmin packageAdmin;

	public OSGIClassResolver(PackageAdmin packageAdmin)
	{
		this.packageAdmin = packageAdmin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netappsid.ejb3.io.IMultiVersionClassResolver#resolveClass(java.lang.String, java.lang.String)
	 */
	@Override
	public Class<?> resolveClass(String className, String versionString, String splitDelimiter) throws ClassNotFoundException
	{
		String[] splits = versionString.split(splitDelimiter);

		String bundleSymname = splits[0];
		String bundleVersion = splits[1];
		Bundle[] bundles = packageAdmin.getBundles(bundleSymname, bundleVersion);

		Bundle bundle = null;

		if (bundles.length > 0)
		{
			bundle = bundles[0];
		}

		Class<?> clazz = Class.forName(className, true, new OSGIClassLoader(bundle));
		if (clazz == null)
		{
			throw new ClassNotFoundException("Could not find bundle for bundle symbolic name " + bundleSymname + " and version " + bundleVersion
					+ " for class " + className);
		}
		return clazz;
	}
}

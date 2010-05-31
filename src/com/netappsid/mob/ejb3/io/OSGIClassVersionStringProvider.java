/**
 * 
 */
package com.netappsid.mob.ejb3.io;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.service.packageadmin.PackageAdmin;

import com.netappsid.mob.ejb3.MobPlugin;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class OSGIClassVersionStringProvider implements IClassVersionStringProvider
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netappsid.ejb3.io.IClassVersionStringProvider#getClassVersionString(java.lang.Class)
	 */
	@Override
	public String getClassVersionString(Class<?> clazz,String splitDelimiter)
	{

		PackageAdmin packageAdmin = MobPlugin.getPackageAdmin();

		// locate correct bundle
		Bundle foundBundle = packageAdmin.getBundle(clazz);

		if (foundBundle != null)
		{
			String symName = foundBundle.getSymbolicName();
			String version = (String) foundBundle.getHeaders().get(Constants.BUNDLE_VERSION);
			return symName + splitDelimiter + version;
		}
		
		return null;
	}

}

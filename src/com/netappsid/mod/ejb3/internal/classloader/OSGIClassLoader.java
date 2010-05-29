/**
 * 
 */
package com.netappsid.mod.ejb3.internal.classloader;

import org.osgi.framework.Bundle;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * encasulate the bundle to use it as classloader
 * 
 * @version $Revision: 1.1 $
 */
public class OSGIClassLoader extends ClassLoader
{
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OSGIClassLoader.class);
	private final Bundle bundle;
	
	public OSGIClassLoader(Bundle bundle)
	{
		this.bundle = bundle;
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException
	{
		return bundle.loadClass(name);
	}
	
}

/**
 * 
 */
package com.netappsid.mob.ejb3.internal.classloader;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 *         encasulate the bundle to use it as classloader
 * 
 * @version $Revision: 1.1 $
 */
public class OSGIClassLoader extends ClassLoader
{
	private static Logger logger = LoggerFactory.getLogger(OSGIClassLoader.class);
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

	@Override
	public URL getResource(String name)
	{
		return bundle.getResource(name);
	}

}

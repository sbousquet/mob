/**
 * 
 */
package com.netappsid.mob.ejb3.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class OSGIObjectInputStream extends ObjectInputStream
{
	private final IMultiVersionClassResolver classResolver;

	private String versionString;

	public OSGIObjectInputStream(final InputStream in, final IMultiVersionClassResolver classResolver) throws IOException
	{
		super(in);
		this.classResolver = classResolver;
	}

	@Override
	protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException
	{
		final boolean hasVersionString = readBoolean();
		versionString = hasVersionString ? readUTF() : null;
		return super.readClassDescriptor();
	}

	@Override
	protected Class<?> resolveClass(final ObjectStreamClass desc) throws IOException, ClassNotFoundException
	{
		if (versionString != null)
		{
			return classResolver.resolveClass(desc.getName(), versionString,"%");
		}
		return super.resolveClass(desc);
	}
}

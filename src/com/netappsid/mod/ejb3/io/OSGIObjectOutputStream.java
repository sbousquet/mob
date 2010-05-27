/**
 * 
 */
package com.netappsid.mod.ejb3.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class OSGIObjectOutputStream extends ObjectOutputStream
{

	private final IClassVersionStringProvider classVersionStringProvider;

	/**
	 * @param out
	 * @throws IOException
	 */
	public OSGIObjectOutputStream(OutputStream out, final IClassVersionStringProvider classVersionStringProvider) throws IOException
	{
		super(out);
		this.classVersionStringProvider = classVersionStringProvider;
	}

	@Override
	protected void writeClassDescriptor(final ObjectStreamClass desc) throws IOException
	{
		final String versionString = classVersionStringProvider.getClassVersionString(desc.forClass(),"%");
		writeBoolean(versionString != null);
		if (versionString != null)
		{
			writeUTF(versionString);
		}
		super.writeClassDescriptor(desc);
	}

}

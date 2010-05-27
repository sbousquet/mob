/**
 * 
 */
package com.netappsid.mod.ejb3.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import com.netappsid.mod.ejb3.EJBServiceLink;
import com.netappsid.mod.ejb3.io.OSGIClassResolver;
import com.netappsid.mod.ejb3.io.OSGIClassVersionStringProvider;
import com.netappsid.mod.ejb3.io.OSGIObjectInputStream;
import com.netappsid.mod.ejb3.io.OSGIObjectOutputStream;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class LocalEJBServiceLink implements EJBServiceLink
{

	private static final Logger LOGGER = Logger.getLogger(LocalEJBServiceLink.class);
	/* (non-Javadoc)
	 * @see com.netappsid.ejb3.EJBServiceLink#enter(java.lang.Object[])
	 */
	@Override
	public Object[] enter(Object[] args)
	{
		try
		{
			return serializeDeserialize(args);
		}
		catch (Exception e)
		{
			LOGGER.error(e, e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.netappsid.ejb3.EJBServiceLink#exit(java.lang.Object)
	 */
	@Override
	public Object exit(Object result)
	{
		try
		{
			return serializeDeserialize(result)[0];
		}
		catch (Exception e)
		{
			LOGGER.error(e, e);
		}
		return null;
	}
	
	
	private static Object[] serializeDeserialize(Object... args) throws Exception
	{
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		ObjectOutputStream oos = null;
		Object[] newArgs = new Object[args.length];

		for (int i = 0; i < args.length; i++)
		{
			if (args[i] != null)
			{
				baos = new ByteArrayOutputStream();
				oos = new OSGIObjectOutputStream(baos, new OSGIClassVersionStringProvider());
				oos.writeObject(args[i]);

				bais = new ByteArrayInputStream(baos.toByteArray());
				newArgs[i] = new OSGIObjectInputStream(bais, new OSGIClassResolver()).readObject();
			}
			else
			{
				newArgs[i] = null;
			}
		}

		return newArgs;
	}

}

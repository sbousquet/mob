/**
 * 
 */
package com.netappsid.mob.ejb3.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class OSGISerializedUtils
{
	private static final Logger LOGGER = LoggerFactory.getLogger(OSGISerializedUtils.class);
	private final OSGIClassResolver osgiClassResolver;
	private final OSGIClassVersionStringProvider osgiClassVersionStringProvider;
	
	public OSGISerializedUtils(OSGIClassResolver osgiClassResolver,OSGIClassVersionStringProvider osgiClassVersionStringProvider)
	{
		this.osgiClassResolver = osgiClassResolver;
		this.osgiClassVersionStringProvider = osgiClassVersionStringProvider;
	}

	public Object[] deserialize(Object... args) throws Exception
	{
		ByteArrayInputStream bais = null;
		Object[] newArgs = new Object[args.length];

		for (int i = 0; i < args.length; i++)
		{
			if (args[i] != null)
			{
				bais = new ByteArrayInputStream((byte[]) args[i]);
				newArgs[i] = new OSGIObjectInputStream(bais, osgiClassResolver).readObject();
			}
			else
			{
				newArgs[i] = null;
			}
		}

		return newArgs;
	}

	public Object serialize(Object toSerialize) throws Exception
	{
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;

		Object result = null;

		if (toSerialize != null)
		{
			baos = new ByteArrayOutputStream();
			oos = new OSGIObjectOutputStream(baos,osgiClassVersionStringProvider);
			oos.writeObject(toSerialize);

			result = baos.toByteArray();
		}

		return result;
	}

	/**
	 * @param parameterTypes
	 * @return
	 */
	public static String[] toStringArray(Class<?>[] parameterTypes)
	{
		String[] names = new String[parameterTypes.length];
	
		for (int i = 0; i < parameterTypes.length; i++)
		{
			names[i] = parameterTypes[i].getName();
		}

		return names;
	}

	

	/**
	 * @param args
	 * @return
	 */
	public Object[] serializeEachObject(Object[] args)
	{
		Object[] serialized = new Object[args.length];

		for (int i = 0; i < args.length; i++)
		{
			try
			{
				serialized[i] = serialize(args[i]);
			}
			catch (Exception e)
			{
				LOGGER.error(e.getMessage(), e);
			}
		}

		return serialized;
	}

	/**
	 * @param args
	 * @return
	 */
	public Object[] deserializeEachObject(Object[] args)
	{
		Object[] deserialized = new Object[args.length];

		for (int i = 0; i < args.length; i++)
		{
			try
			{
				deserialized[i] = deserialize(args[i]);
			}
			catch (Exception e)
			{
				LOGGER.error(e.getMessage(), e);
			}
		}

		return deserialized;
	}

}

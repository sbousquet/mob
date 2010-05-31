/**
 * 
 */
package com.netappsid.mob.ejb3.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;



/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class OSGISerializedUtils
{
	private static final Logger LOGGER = Logger.getLogger(OSGISerializedUtils.class);

	public static Object[] deserialize(Object... args) throws Exception
	{
		ByteArrayInputStream bais = null;
		Object[] newArgs = new Object[args.length];

		for (int i = 0; i < args.length; i++)
		{
			if (args[i] != null)
			{
				bais = new ByteArrayInputStream((byte[]) args[i]);
				newArgs[i] = new OSGIObjectInputStream(bais, new OSGIClassResolver()).readObject();
			}
			else
			{
				newArgs[i] = null;
			}
		}

		return newArgs;
	}

	public static Object serialize(Object toSerialize) throws Exception
	{
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;

		Object result = null;

		if (toSerialize != null)
		{
			baos = new ByteArrayOutputStream();
			oos = new OSGIObjectOutputStream(baos, new OSGIClassVersionStringProvider());
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
	public static Object[] serializeEachObject(Object[] args)
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
				LOGGER.error(e, e);
			}
		}

		return serialized;
	}

	/**
	 * @param args
	 * @return
	 */
	public static Object[] deserializeEachObject(Object[] args)
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
				LOGGER.error(e, e);
			}
		}

		return deserialized;
	}

}

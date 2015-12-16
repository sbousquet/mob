/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netappsid.mob.ejb3.EJBServiceLink;
import com.netappsid.mob.ejb3.io.OSGIClassResolver;
import com.netappsid.mob.ejb3.io.OSGIClassVersionStringProvider;
import com.netappsid.mob.ejb3.io.OSGIObjectInputStream;
import com.netappsid.mob.ejb3.io.OSGIObjectOutputStream;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class FakeRemoteEJBServiceLink implements EJBServiceLink
{

	private static final Logger LOGGER = LoggerFactory.getLogger(FakeRemoteEJBServiceLink.class);
	private final PackageAdmin admin;
	private boolean ignoreSerialization = false;

	public FakeRemoteEJBServiceLink(PackageAdmin admin)
	{
		this.admin = admin;
		String systemProperty = System.getProperty("ignoreSerialization");
		if (systemProperty != null)
		{
			ignoreSerialization = Boolean.parseBoolean(systemProperty);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netappsid.ejb3.EJBServiceLink#enter(java.lang.Object[])
	 */
	@Override
	public Object[] enter(Object[] args)
	{
		try
		{
			if (ignoreSerialization)
			{
				return args;
			}
			else
			{
				return serializeDeserialize(args);
			}
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netappsid.ejb3.EJBServiceLink#exit(java.lang.Object)
	 */
	@Override
	public Object exit(Object result)
	{
		try
		{
			if (ignoreSerialization)
			{
				return result;
			}
			else
			{
				return serializeDeserialize(result)[0];
			}
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	private Object[] serializeDeserialize(Object... args) throws Exception
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
				oos = new OSGIObjectOutputStream(baos, new OSGIClassVersionStringProvider(admin));
				oos.writeObject(args[i]);

				bais = new ByteArrayInputStream(baos.toByteArray());
				newArgs[i] = new OSGIObjectInputStream(bais, new OSGIClassResolver(admin)).readObject();
			}
			else
			{
				newArgs[i] = null;
			}
		}

		return newArgs;
	}

}

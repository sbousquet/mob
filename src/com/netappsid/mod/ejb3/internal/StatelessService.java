/**
 * 
 */
package com.netappsid.mod.ejb3.internal;

import java.util.concurrent.ExecutorService;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyObject;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.naming.NamingException;
import javax.naming.Reference;

import com.netappsid.mod.ejb3.internal.jndi.Ejb3Ref;


/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.8 $
 */
public class StatelessService extends AbstractService
{
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(StatelessService.class);
	private Class<?> serviceClass;
	private final Class<?> originalClass;

	public StatelessService(ExecutorService executorService, Class<?> serviceClass, EJB3BundleUnit bundleUnit)
	{
		super(executorService, bundleUnit);
		originalClass = serviceClass;
		try
		{
			this.serviceClass = modifyClass(serviceClass);
		}
		catch (Exception e)
		{
			logger.error(e, e);
		}

	}

	/**
	 * @return
	 * @see com.netappsid.mod.ejb3.internal.EJb3Service#getService()
	 */
	@Override
	public Object getContent()
	{
		return getProxy();
	}
	
	/* (non-Javadoc)
	 * @see com.netappsid.mod.ejb3.internal.EJb3Service#getProxy()
	 */
	@Override
	public Object getProxy()
	{
		try
		{
			Object inst = serviceClass.newInstance();
			((ProxyObject) inst).setHandler(getMethodHandler(originalClass));

			return inst;
		}
		catch (InstantiationException e)
		{
			logger.error(e, e);
		}
		catch (IllegalAccessException e)
		{
			logger.error(e, e);
		}

		return null;
	}

	@Override
	public Class<?> getLocalInterface()
	{
		// we must get the super class beacause is a proxy class
		if (serviceClass.getSuperclass().isAnnotationPresent(Local.class))
		{
			Class[] value = serviceClass.getSuperclass().getAnnotation(Local.class).value();

			if (value.length == 1)
			{
				return value[0];
			}
		}

		for (Class inter : serviceClass.getSuperclass().getInterfaces())
		{
			if (inter.isAnnotationPresent(Local.class))
			{
				return inter;
			}
		}
		return null;
	}

	@Override
	public Class<?> getRemoteInterface()
	{
		// we must get the super class beacause is a proxy class
		if (serviceClass.getSuperclass().isAnnotationPresent(Remote.class))
		{
			Class[] value = serviceClass.getSuperclass().getAnnotation(Remote.class).value();

			if (value.length == 1)
			{
				return value[0];
			}
		}

		for (Class inter : serviceClass.getSuperclass().getInterfaces())
		{
			if (inter.isAnnotationPresent(Remote.class))
			{
				return inter;
			}
		}
		return null;
	}

	@Override
	public String getName()
	{
		return serviceClass.getSuperclass().getSimpleName();
	}

	@Override
	public Class<?> getBeanClass()
	{
		return originalClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.naming.Referenceable#getReference()
	 */
	@Override
	public Reference getReference() throws NamingException
	{
		return new Ejb3Ref(getBeanClass().getName(), this, null, null);
	}

	@Override
	protected MethodHandler getMethodHandler(Class<?> originalClass) throws InstantiationException, IllegalAccessException
	{
		return new StatelessMethodHandler(originalClass, getEjbLink(), getExecutor(), getBundleUnit());
	}

}

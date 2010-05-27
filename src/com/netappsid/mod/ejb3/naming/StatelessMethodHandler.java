package com.netappsid.mod.ejb3.naming;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

import javassist.util.proxy.MethodHandler;

import com.netappsid.mod.ejb3.EJB3ServiceHandler;
import com.netappsid.mod.ejb3.EJBServiceLink;
import com.netappsid.mod.ejb3.internal.EJB3ThreadWorker;
import com.netappsid.mod.ejb3.internal.InvocationHandler;

/**
 * 
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 */
public class StatelessMethodHandler implements MethodHandler, EJB3ServiceHandler
{
	private EJBServiceLink link;
	private ExecutorService executorService;
	private final EJB3BundleUnit bundleUnit;
	private final Class<?> serviceClass;

	public StatelessMethodHandler(Class<?> serviceClass, EJBServiceLink link, ExecutorService executorService, EJB3BundleUnit bundleUnit)
			throws InstantiationException, IllegalAccessException
	{
		this.serviceClass = serviceClass;
		this.link = link;
		this.executorService = executorService;
		this.bundleUnit = bundleUnit;

	}

	public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable
	{
		if (thisMethod.getDeclaringClass().equals(EJB3ServiceHandler.class))
		{
			return thisMethod.invoke(this, args);
		}
		
		//stateless every time you enter you need to create a new instance
		Object serviceInstance = serviceClass.newInstance();

		final InvocationHandler ejb3Runnable = new InvocationHandler(link, bundleUnit, serviceInstance, thisMethod, args);

		return Thread.currentThread() instanceof EJB3ThreadWorker ? ejb3Runnable.call() : executorService.submit(ejb3Runnable).get();
	}

	public void setEJBLink(EJBServiceLink link)
	{
		this.link = link;
	}

	@Override
	public EJB3BundleUnit getBundleUnit()
	{
		return bundleUnit;
	}
}

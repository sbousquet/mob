package com.netappsid.mod.ejb3.internal.interceptors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

public class InterceptorHandler
{
	private Object interceptor;
	private Method notifyMethod;
	
	public InterceptorHandler(Object interceptor,Method notifyMethod)
	{
		this.interceptor = interceptor;
		this.notifyMethod = notifyMethod;
	}
	
	public Object invoke(InvocationContext context) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		return notifyMethod.invoke(interceptor, context);
	}
	
}

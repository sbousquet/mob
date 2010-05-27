package com.netappsid.mod.ejb3.internal.interceptors;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJBException;
import javax.interceptor.AroundInvoke;

public class Interceptors
{
	private Map<String, List<InterceptorHandler>> interceptorByServices = new HashMap<String, List<InterceptorHandler>>();
	private Set<String> allServices = Collections.emptySet();

	public void addAllServices(Collection<String> servicesName)
	{
		allServices = new HashSet<String>(servicesName);
	}

	public void registerInterceptor(String serviceName, Class<?> interceptorClass) throws InstantiationException, IllegalAccessException
	{
		// default interceptor one instance by service
		if (serviceName.equals("*"))
		{
			Method toNotify = getMethodToNotify(interceptorClass);

			for (String service : allServices)
			{
				register(service, new InterceptorHandler(interceptorClass.newInstance(), toNotify));
			}

		}
		else if (allServices.contains(serviceName))
		{
			Method toNotify = getMethodToNotify(interceptorClass);
			register(serviceName, new InterceptorHandler(interceptorClass.newInstance(), toNotify));
		}
		else
		{
			throw new EJBException("the service " + serviceName + " doesn't exist");
		}
	}

	private void register(String service, InterceptorHandler interceptorHandler)
	{
		if (interceptorByServices.containsKey(service))
		{
			interceptorByServices.get(service).add(interceptorHandler);
		}
		else
		{
			List<InterceptorHandler> handlers = new ArrayList<InterceptorHandler>();
			handlers.add(interceptorHandler);

			interceptorByServices.put(service, handlers);
		}
	}

	private Method getMethodToNotify(Class<?> interceptorClass)
	{
		Method[] methods = interceptorClass.getMethods();
		
		for (Method method : methods)
		{
			if(method.isAnnotationPresent(AroundInvoke.class))
			{
				return method;
			}
		}
		
		return null;
	}

	public List<InterceptorHandler> getInterceptors(String serviceName)
	{
		return interceptorByServices.get(serviceName);
	}

}

package com.netappsid.mob.ejb3.remote;

import java.security.Principal;
import java.util.concurrent.Callable;

import javax.naming.InitialContext;

import com.netappsid.mob.ejb3.EJB3ServiceHandler;
import com.netappsid.mob.ejb3.internal.EmptyEJBServiceLink;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 */
public class EJB3CallHandler<T> implements Callable<T>
{
//	public static final String JNDI_CONTEXT = "JNDI_CONTEXT";
//	public static final String EJB3_BEAN = "EJB3_BEAN";
//	public static final String METHOD_NAME = "METHOD_NAME";
//	public static final String PARAMETER_TYPES = "PARAMETER_TYPES";
//	public static final String PARAMETERS = "PARAMETERS";
//	public static final String PRINCIPAL = "PRINCIPAL";
	
	private Principal principal;
	private String jndiName;
	private String methodName;
	private Class[] parameterTypes;
	private Object[] args;
	

//	public Object handle(Message msg, Map<String, Object> messageValues) throws Exception
//	{
//		SecurityAssociation.setPrincipal((Principal) OSGISerializedUtils.deserialize(messageValues.get(PRINCIPAL))[0]);
//
//		String context = (String) messageValues.get(JNDI_CONTEXT);
//		String bean = (String) messageValues.get(EJB3_BEAN);
//
//		Object lookup = new InitialContext().lookup(context + "/" + bean + "/remote");
//
//		if (lookup instanceof EJB3ServiceHandler)
//		{
//			((EJB3ServiceHandler) lookup).setEJBLink(EmptyEJBServiceLink.getInstance());
//		}
//
//		String methodName = (String) messageValues.get(METHOD_NAME);
//		String[] parameterTypesName = (String[]) messageValues.get(PARAMETER_TYPES);
//
//		Class[] parameterTypes = new Class[parameterTypesName.length];
//
//		for (int i = 0; i < parameterTypesName.length; i++)
//		{
//			parameterTypes[i] = ClassUtils.forName(parameterTypesName[i], lookup.getClass().getClassLoader());
//		}
//		Object[] args = (Object[]) OSGISerializedUtils.deserialize(messageValues.get(PARAMETERS))[0];
//
//		Object result;
//		try
//		{
//			result = lookup.getClass().getMethod(methodName, parameterTypes).invoke(lookup, args);
//		}
//		catch (Throwable e)
//		{
//			return OSGISerializedUtils.serialize(e);
//		}
//
//		return OSGISerializedUtils.serialize(result);
//	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public T call() throws Exception
	{
		Object lookup = new InitialContext().lookup(jndiName);

		if (lookup instanceof EJB3ServiceHandler)
		{
			((EJB3ServiceHandler) lookup).setEJBLink(EmptyEJBServiceLink.getInstance());
		}

		return (T) lookup.getClass().getMethod(methodName, parameterTypes).invoke(lookup, args);
	}

}

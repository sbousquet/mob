package com.netappsid.mob.ejb3.internal.context;

import java.security.Identity;
import java.security.Principal;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.xml.rpc.handler.MessageContext;

import com.netappsid.mob.ejb3.security.SecurityAssociation;

public class BeanSessionContext implements SessionContext
{

	private final UserTransaction userTransaction;

	public BeanSessionContext(UserTransaction userTransaction)
	{
		this.userTransaction = userTransaction;
	}

	@Override
	public Identity getCallerIdentity()
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public Principal getCallerPrincipal()
	{
		return SecurityAssociation.getCallerPrincipal();
	}

	@Override
	public Map<String, Object> getContextData()
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public EJBHome getEJBHome()
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public EJBLocalHome getEJBLocalHome()
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public Properties getEnvironment()
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public boolean getRollbackOnly() throws IllegalStateException
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public TimerService getTimerService() throws IllegalStateException
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public UserTransaction getUserTransaction() throws IllegalStateException
	{
		return userTransaction;
	}

	@Override
	public boolean isCallerInRole(Identity arg0)
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public boolean isCallerInRole(String arg0)
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public Object lookup(String arg0)
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public void setRollbackOnly() throws IllegalStateException
	{
		try
		{
			userTransaction.setRollbackOnly();
		}
		catch (SystemException e)
		{
			throw new IllegalStateException(e);
		}
	}

	@Override
	public <T> T getBusinessObject(Class<T> arg0) throws IllegalStateException
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public EJBLocalObject getEJBLocalObject() throws IllegalStateException
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public EJBObject getEJBObject() throws IllegalStateException
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public Class getInvokedBusinessInterface() throws IllegalStateException
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public MessageContext getMessageContext() throws IllegalStateException
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

	@Override
	public boolean wasCancelCalled() throws IllegalStateException
	{
		throw new UnsupportedOperationException("Depecated used get caller principal");
	}

}

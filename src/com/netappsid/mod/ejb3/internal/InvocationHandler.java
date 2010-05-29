/**
 * 
 */
package com.netappsid.mod.ejb3.internal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.InvocationContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.netappsid.mod.ejb3.EJBServiceLink;
import com.netappsid.mod.ejb3.internal.interceptors.InterceptorHandler;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class InvocationHandler implements Callable<Object>
{

	private static Logger logger = Logger.getLogger(InvocationHandler.class);

	private EJBServiceLink link;

	private boolean hasStartTransaction = false;

	private final Object self;
	private final Method thisMethod;
	private Object[] args;

	private final EJB3BundleUnit bundleUnit;

	private class InvocationContextImpl implements InvocationContext
	{

		private Iterator<InterceptorHandler> interceptorIterator;

		public InvocationContextImpl()
		{}

		public InvocationContextImpl(Iterator<InterceptorHandler> iterator)
		{
			this.interceptorIterator = iterator;
		}

		@Override
		public Map<String, Object> getContextData()
		{
			return new HashMap<String, Object>();
		}

		@Override
		public Method getMethod()
		{
			return thisMethod;
		}

		@Override
		public Object[] getParameters()
		{
			return args;
		}

		@Override
		public Object getTarget()
		{
			return self;
		}

		@Override
		public Object proceed() throws Exception
		{
			// you have to chain the interceptor
			if (interceptorIterator != null && interceptorIterator.hasNext())
			{
				InterceptorHandler next = interceptorIterator.next();
				return next.invoke(this);
			}
			else
			{
				return thisMethod.invoke(self, args); // execute the original method.
			}
		}

		@Override
		public void setParameters(Object[] parameters)
		{
			args = parameters;
		}

	}

	/**
	 * 
	 */
	public InvocationHandler(EJBServiceLink link, EJB3BundleUnit bundleUnit, Object self, Method thisMethod, Object[] args)
	{
		this.link = link;
		this.bundleUnit = bundleUnit;
		this.self = self;
		this.thisMethod = thisMethod;
		this.args = args;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public Object call() throws Exception
	{
		try
		{

			if (isTransactionRequired(thisMethod))
			{
				UserTransaction userTransaction = getUserTransaction();

				if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION)
				{
					args = link.enter(args);
				}

				// before
				if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION)
				{
					userTransaction.begin();
					setHasStartTransaction(true);
				}

				bundleUnit.inject(self);

				List<InterceptorHandler> interceptors = bundleUnit.getInterceptors(self.getClass().getSimpleName());

				InvocationContext invocationContext = null;

				if (interceptors != null)
				{
					Iterator<InterceptorHandler> iterator = interceptors.iterator();
					invocationContext = new InvocationContextImpl(iterator);
				}
				else
				{
					invocationContext = new InvocationContextImpl();
				}
				Object result = invocationContext.proceed();

				// after
				if (isHasStartTransaction())
				{
					bundleUnit.flush();

					UserTransaction toCommit = getUserTransaction();
					toCommit.commit();

				}

				if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION)
				{
					result = link.exit(result);
				}

				return result;
			}
			else
			{

				UserTransaction userTransaction = getUserTransaction();

				bundleUnit.inject(self);

				if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION)
				{
					args = link.enter(args);
				}

				Object result = thisMethod.invoke(self, args); // execute the original method.

				if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION)
				{
					result = link.exit(result);
				}

				return result;
			}
		}
		catch (Exception e)
		{
			logger.error(e, e);

			//you want to rollback only when you has start the transaction 
			if (isHasStartTransaction())
			{
				UserTransaction userTransaction = getUserTransaction();
				userTransaction.rollback();
			}
			throw e;
		}
		finally
		{
			bundleUnit.getStatelessPool().recycle(self);
			
			if (isHasStartTransaction())
			{
				bundleUnit.close();
				setHasStartTransaction(false);
			}
		}

	}

	/**
	 * @return
	 * @throws NamingException
	 */
	protected UserTransaction getUserTransaction() throws NamingException
	{
		return new UserTransactionImp();
	}

	/**
	 * Check if the given method has a TransactionAttribute annotation. If that annotation is NEVER it will return false. Otherwise it will return true;
	 * 
	 * @param method
	 *            the method to check for the TransactionAttribute annotation
	 * @return false if a TransactionAttributeType.NEVER is found, true otherwise
	 */
	private static boolean isTransactionRequired(Method method)
	{
		boolean result = true;

		try
		{
			TransactionAttribute transaction = null;
			for (Object annotation : method.getAnnotations())
			{
				if (TransactionAttribute.class.isAssignableFrom(annotation.getClass()))
				{
					transaction = (TransactionAttribute) annotation;
					if (transaction.value() == TransactionAttributeType.NEVER)
					{
						result = false;
					}
					else if (transaction.value() == TransactionAttributeType.SUPPORTS)
					{
						result = false;
					}
					break;
				}
			}
		}
		catch (Exception exc)
		{
			// I will assume, if an error occurs that the transaction is required
			logger.debug(exc, exc);
		}
		return result;
	}

	/**
	 * @return the hasStartTransaction
	 */
	private boolean isHasStartTransaction()
	{
		return hasStartTransaction;
	}

	/**
	 * @param hasStartTransaction the hasStartTransaction to set
	 */
	private void setHasStartTransaction(boolean hasStartTransaction)
	{
		this.hasStartTransaction = hasStartTransaction;
	}

	

}

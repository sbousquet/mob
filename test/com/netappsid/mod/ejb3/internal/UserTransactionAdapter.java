/**
 * 
 */
package com.netappsid.mod.ejb3.internal;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class UserTransactionAdapter implements UserTransaction
{

	/* (non-Javadoc)
	 * @see javax.transaction.UserTransaction#begin()
	 */
	@Override
	public void begin() throws NotSupportedException, SystemException
	{}

	/* (non-Javadoc)
	 * @see javax.transaction.UserTransaction#commit()
	 */
	@Override
	public void commit() throws HeuristicMixedException, HeuristicRollbackException, IllegalStateException, RollbackException, SecurityException,
			SystemException
	{}

	/* (non-Javadoc)
	 * @see javax.transaction.UserTransaction#getStatus()
	 */
	@Override
	public int getStatus() throws SystemException
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.transaction.UserTransaction#rollback()
	 */
	@Override
	public void rollback() throws IllegalStateException, SecurityException, SystemException
	{}

	/* (non-Javadoc)
	 * @see javax.transaction.UserTransaction#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException
	{}

	/* (non-Javadoc)
	 * @see javax.transaction.UserTransaction#setTransactionTimeout(int)
	 */
	@Override
	public void setTransactionTimeout(int arg0) throws SystemException
	{}

}

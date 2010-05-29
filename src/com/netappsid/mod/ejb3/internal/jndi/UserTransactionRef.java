/**
 * 
 */
package com.netappsid.mod.ejb3.internal.jndi;

import javax.naming.RefAddr;
import javax.transaction.UserTransaction;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class UserTransactionRef extends RefAddr
{

	private final UserTransaction userTransaction;

	/**
	 * @param addrType
	 */
	public UserTransactionRef(String addrType, UserTransaction userTransaction)
	{
		super(addrType);
		this.userTransaction = userTransaction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.naming.RefAddr#getContent()
	 */
	@Override
	public Object getContent()
	{
		return userTransaction;
	}

}

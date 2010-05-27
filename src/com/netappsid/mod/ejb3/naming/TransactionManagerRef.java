/**
 * 
 */
package com.netappsid.mod.ejb3.naming;

import javax.naming.RefAddr;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class TransactionManagerRef extends RefAddr
{

	/**
	 * @param addrType
	 */
	protected TransactionManagerRef(String addrType)
	{
		super(addrType);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.naming.RefAddr#getContent()
	 */
	@Override
	public Object getContent()
	{
		// TODO Auto-generated method stub
		return null;
	}

//	private final TMService jotm;
//
//	/**
//	 * @param addrType
//	 */
//	public TransactionManagerRef(String addrType,TMService jotm)
//	{
//		super(addrType);
//		this.jotm = jotm;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see javax.naming.RefAddr#getContent()
//	 */
//	@Override
//	public Object getContent()
//	{
//		return jotm.getTransactionManager() ;
//	}

}

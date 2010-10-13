/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import com.netappsid.mob.ejb3.EJBServiceLink;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class EmptyEJBServiceLink implements EJBServiceLink
{
	
	private static final EmptyEJBServiceLink instance = new EmptyEJBServiceLink();

	/* (non-Javadoc)
	 * @see com.netappsid.mob.ejb3.EJBServiceLink#enter(java.lang.Object[])
	 */
	@Override
	public Object[] enter(Object[] args)
	{
		return args;
	}

	/* (non-Javadoc)
	 * @see com.netappsid.mob.ejb3.EJBServiceLink#exit(java.lang.Object)
	 */
	@Override
	public Object exit(Object result)
	{
		return result;
	}

	/**
	 * @return
	 */
	public static EJBServiceLink getInstance()
	{
		return instance;
	}

}

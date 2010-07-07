/**
 * 
 */
package com.netappsid.mob.ejb3;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class EJBContainerImpl extends EJBContainer
{

	/* (non-Javadoc)
	 * @see javax.ejb.embeddable.EJBContainer#close()
	 */
	@Override
	public void close()
	{}

	/* (non-Javadoc)
	 * @see javax.ejb.embeddable.EJBContainer#getContext()
	 */
	@Override
	public Context getContext()
	{
		return null;
	}

}

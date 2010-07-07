/**
 * 
 */
package com.netappsid.mob.ejb3;

import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.ejb.spi.EJBContainerProvider;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class EJBContainerProviderImpl implements EJBContainerProvider
{
	
	/**
	 * 
	 */
	public EJBContainerProviderImpl()
	{
		System.out.println();
	}

	/* (non-Javadoc)
	 * @see javax.ejb.spi.EJBContainerProvider#createEJBContainer(java.util.Map)
	 */
	@Override
	public EJBContainer createEJBContainer(Map<?, ?> arg0) throws EJBException
	{
		return new EJBContainerImpl();
	}

}

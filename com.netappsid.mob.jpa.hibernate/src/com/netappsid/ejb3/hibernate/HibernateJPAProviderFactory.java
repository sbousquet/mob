/**
 * 
 */
package com.netappsid.ejb3.hibernate;

import com.netappsid.mob.ejb3.JPAProvider;
import com.netappsid.mob.ejb3.JPAProviderFactory;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class HibernateJPAProviderFactory implements JPAProviderFactory
{

	/* (non-Javadoc)
	 * @see com.netappsid.mob.ejb3.JPAProviderFactory#create()
	 */
	@Override
	public JPAProvider create()
	{
		return new HibernateJPAProvider();
	}

}

/**
 * 
 */
package com.netappsid.mob.ejb3.test;

import com.netappsid.mob.ejb3.internal.EJB3LifecycleManager;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class DummyLifecycleManager implements EJB3LifecycleManager
{

	/* (non-Javadoc)
	 * @see com.netappsid.mod.ejb3.internal.EJB3LifecycleManager#create(java.lang.Class)
	 */
	@Override
	public <T> T create(Class<T> toCreate) throws InstantiationException, IllegalAccessException
	{
		return toCreate.newInstance();
	}

	/* (non-Javadoc)
	 * @see com.netappsid.mod.ejb3.internal.EJB3LifecycleManager#destroy(java.lang.Object)
	 */
	@Override
	public void destroy(Object toDestroy)
	{}

}

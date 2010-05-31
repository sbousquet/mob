/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public interface EJB3LifecycleManager
{
	public <T> T create(Class<T> toCreate) throws InstantiationException, IllegalAccessException;
	public void destroy(Object toDestroy);
	
}

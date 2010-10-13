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
	public <T> T create(Class<T> toCreate) throws Exception;
	public void destroy(Object toDestroy) throws Exception;
	
}

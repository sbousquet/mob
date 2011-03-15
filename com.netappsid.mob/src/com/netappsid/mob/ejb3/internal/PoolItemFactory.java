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
public interface PoolItemFactory<T>
{
	public T create() throws Exception;
	public void destroy(T toDestroy) throws Exception;
	
}

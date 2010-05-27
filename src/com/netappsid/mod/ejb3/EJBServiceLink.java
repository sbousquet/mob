/**
 * 
 */
package com.netappsid.mod.ejb3;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public interface EJBServiceLink
{

	/**
	 * @param args
	 * @return
	 */
	Object[] enter(Object[] args);

	/**
	 * @param result
	 * @return
	 */
	Object exit(Object result);
	
}

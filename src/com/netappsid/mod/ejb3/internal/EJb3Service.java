/**
 * 
 */
package com.netappsid.mod.ejb3.internal;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.1 $
 */
public interface EJb3Service
{
	public static final String EJB3SERVICE = "ejb3Service";
	
	public Class<?> getRemoteInterface();
	public Class<?> getLocalInterface();
	public Class<?> getBeanClass();
	public String getName();
	/**
	 * @return
	 */
	public Object getProxy();
}

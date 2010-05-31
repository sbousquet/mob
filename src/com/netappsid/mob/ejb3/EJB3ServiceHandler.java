/**
 * 
 */
package com.netappsid.mob.ejb3;

import com.netappsid.mob.ejb3.internal.EJB3BundleUnit;


/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public interface EJB3ServiceHandler
{
	public void setEJBLink(EJBServiceLink link);
	public EJB3BundleUnit getBundleUnit();
}

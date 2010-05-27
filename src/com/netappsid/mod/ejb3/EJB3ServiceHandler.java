/**
 * 
 */
package com.netappsid.mod.ejb3;

import com.netappsid.mod.ejb3.naming.EJB3BundleUnit;


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

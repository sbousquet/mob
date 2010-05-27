/**
 * 
 */
package com.netappsid.mod.ejb3.naming;

import javax.naming.Reference;
import javax.naming.StringRefAddr;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class EJB3BundleUnitReference extends Reference
{

	/**
	 * @param className
	 */
	public EJB3BundleUnitReference(String className,String name)
	{
		super(className);
		
		 StringRefAddr refAddr = new StringRefAddr("ear", name);
		 add(refAddr);
	}

}

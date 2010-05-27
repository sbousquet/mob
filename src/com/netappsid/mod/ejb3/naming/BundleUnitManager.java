/**
 * 
 */
package com.netappsid.mod.ejb3.naming;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.2 $
 */
public class BundleUnitManager
{
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BundleUnitManager.class);
	
	private static Map<String, EJB3BundleUnit> bundleUnits = new HashMap<String, EJB3BundleUnit>();
	
	
	public static void addBundleUnit(EJB3BundleUnit bundleUnit)
	{
		bundleUnits.put(bundleUnit.getName(), bundleUnit);
	}
	
	public static EJB3BundleUnit getBundleUnit(String name)
	{
		return bundleUnits.get(name);
	}
	
}

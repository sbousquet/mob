/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.2 $
 */
public class BundleUnitManager
{
	private static Logger logger = LoggerFactory.getLogger(BundleUnitManager.class);
	
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

/**
 * 
 */
package com.netappsid.mob.ejb3.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import com.netappsid.mob.ejb3.internal.BundleUnitManager;
import com.netappsid.mob.ejb3.internal.EJB3BundleUnit;
import com.netappsid.mob.ejb3.internal.TransactionScopedEntityManager;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class JNDIEntityManagerFactory implements ObjectFactory
{
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception
	{
		if (!(obj instanceof Reference))
			return null;

		Reference ref = (Reference) obj;

		String bundleUnitName = (String) ref.get("EntityManager").getContent();

		EJB3BundleUnit bundleUnit = BundleUnitManager.getBundleUnit(bundleUnitName);
		return new TransactionScopedEntityManager(bundleUnit);

	}
}

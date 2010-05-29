
package com.netappsid.mod.ejb3.internal;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

/**
 * Object factory for EJBs.
 * 
 * @author Remy Maucherat
 * @version $Revision: 1.1 $ $Date: 2009-07-21 21:15:36 $
 */

public class Ejb3Factory implements ObjectFactory
{

	// ----------------------------------------------------------- Constructors

	// -------------------------------------------------------------- Constants

	// ----------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Public Methods

	// -------------------------------------------------- ObjectFactory Methods

	/**
	 * Crete a new EJB instance.
	 * 
	 * @param obj
	 *            The reference object describing the DataSource
	 */
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception
	{

		if (obj instanceof Reference)
		{
			Reference ref = (Reference) obj;

			RefAddr linkRefAddr = ref.get(EJb3Service.EJB3SERVICE);
			if (linkRefAddr != null)
			{
				// Retrieving the EJB impl
				Object beanObj = linkRefAddr.getContent();
				return beanObj;
			}

		}
		return null;
	}

}

package com.netappsid.mob.ejb3.jndi;

import java.net.URL;

import javax.naming.Context;
import javax.naming.RefAddr;
import javax.naming.Reference;

/**
 * Represents a reference address to an EJB3 service.
 * 
 */

public class Ejb3Ref extends Reference
{

	// -------------------------------------------------------------- Constants

	/**
	 * Default factory for this reference.
	 */
	public static final String DEFAULT_FACTORY = Ejb3Factory.class.getName();

	// ----------------------------------------------------------- Constructors

	/**
	 * EJB3 Reference.
	 * 
	 * @param ejbType
	 *            EJB type
	 * @param beanClass
	 *            Home interface classname
	 * @param remote
	 *            Remote interface classname
	 * @param linkLocal
	 *            EJB link
	 */
	public Ejb3Ref(String beanClass, RefAddr ejb3Service, String factory, String factoryLocation)
	{
		super(beanClass, factory, factoryLocation);

		if (ejb3Service != null)
		{
			add(ejb3Service);
		}

	}

	// ----------------------------------------------------- Instance Variables

	// -------------------------------------------------------- RefAddr Methods

	// ------------------------------------------------------ Reference Methods

	/**
	 * Retrieves the class name of the factory of the object to which this reference refers.
	 */
	@Override
	public String getFactoryClassName()
	{
		String factory = super.getFactoryClassName();
		if (factory != null)
		{
			return factory;
		}
		else
		{
			factory = System.getProperty(Context.OBJECT_FACTORIES);
			if (factory != null)
			{
				return null;
			}
			else
			{
				return DEFAULT_FACTORY;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.naming.Reference#getFactoryClassLocation()
	 */
	@Override
	public String getFactoryClassLocation()
	{
		//com.netappsid.ejb3.naming
		URL resource = Ejb3Factory.class.getResource("/");
		return resource.toString();
	}
	
	

	// ------------------------------------------------------------- Properties

}

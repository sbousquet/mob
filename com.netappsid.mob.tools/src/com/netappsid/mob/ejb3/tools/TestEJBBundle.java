package com.netappsid.mob.ejb3.tools;

import java.util.ArrayList;
import java.util.List;

import com.netappsid.mob.ejb3.internal.classloader.OSGIClassLoader;
import com.netappsid.mob.ejb3.osgi.EJB3Deployer;
import com.netappsid.mob.ejb3.xml.PersistenceUnitInfoXml;

public class TestEJBBundle  extends EmbeddedDerbyBundle
{
	private final List<Class> ejbClasses = new ArrayList<Class>();
	
	public TestEJBBundle(String bundleName, String resourceName, PersistenceUnitInfoXml persistenceUnitInfo)
	{
		super(bundleName, resourceName, "jdbc:derby:memory:" + resourceName + ";create=true", "", "", persistenceUnitInfo);
	}
	
	/**
	 * Adds a class to deploy.  This must be called before the bundle is deployed.
	 */
	public void addEJB3Class(Class ejbClass)
	{
		ejbClasses.add(ejbClass);
	}
	
	protected final void configureDeployer(EJB3Deployer deployer)
	{
		for (Class ejbClass : ejbClasses)
		{
			deployer.addEJB3Class(ejbClass);
		}
	}
	
	protected final PersistenceUnitInfoXml configurePersistenceUnitInfoXml()
	{
		return null;
	}

	
}

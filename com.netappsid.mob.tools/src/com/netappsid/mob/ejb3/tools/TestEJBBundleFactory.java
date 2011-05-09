package com.netappsid.mob.ejb3.tools;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.packageadmin.PackageAdmin;

import com.netappsid.mob.ejb3.MobPlugin;
import com.netappsid.mob.ejb3.internal.classloader.OSGIClassLoader;
import com.netappsid.mob.ejb3.xml.PersistenceUnitInfoXml;

public class TestEJBBundleFactory
{
	private String bundleName;
	private String resourceName = "QueryBundleTest";
	private String persistenceProviderName = "org.hibernate.ejb.HibernatePersistence";
	private Map<Object, Object> properties = new HashMap<Object, Object>();

	/**
	 * 
	 */
	public TestEJBBundleFactory(String bundleName)
	{
		this.bundleName = bundleName;
		properties.put("hibernate.dialect", "com.netappsid.dialect.ExtendedDerbyDialect");
		properties.put("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider");
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
	}
	
	
	public void setPersistenceProviderClassName(String persistenceProviderName)
	{
		this.persistenceProviderName = persistenceProviderName;
	}

	public void putProperty(Object key, Object value)
	{
		properties.put(key, value);
	}
	

	public TestEJBBundle createQueryBundle(Class<?> fromEntity,Class<?>...ejbServices) throws IntrospectionException
	{
		OSGIClassLoader osgiClassLoader = new OSGIClassLoader(MobPlugin.getService(PackageAdmin.class).getBundle(fromEntity));
		
		final PersistenceUnitInfoXml persistenceInfo = new PersistenceUnitInfoXml();

		persistenceInfo.setPersistenceUnitName(bundleName);
		persistenceInfo.setTransactionType("JTA");
		persistenceInfo.setPersistenceProviderClassName(persistenceProviderName);
		persistenceInfo.setJtaDatasoure(resourceName);

		persistenceInfo.getProperties().putAll(properties);

		TestEJBBundle bundle = new TestEJBBundle(bundleName, resourceName, persistenceInfo);
		bundle.setClassLoader(osgiClassLoader);
		
		for (Class ejbClass : JPAHelper.buildEntityGraph(fromEntity))
		{
			bundle.addEJB3Class(ejbClass);
		}
		
		for (Class<?> class1 : ejbServices)
		{
			bundle.addEJB3Class(class1);
		}

		return bundle;
	}
}

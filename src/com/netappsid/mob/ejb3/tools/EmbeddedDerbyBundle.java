package com.netappsid.mob.ejb3.tools;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDriver;

import com.netappsid.mob.ejb3.DatasourceProvider;
import com.netappsid.mob.ejb3.MobPlugin;
import com.netappsid.mob.ejb3.osgi.OSGIEJB3Bundle;
import com.netappsid.mob.ejb3.xml.PersistenceUnitInfoXml;

public abstract class EmbeddedDerbyBundle extends OSGIEJB3Bundle
{
	public EmbeddedDerbyBundle(String bundleName, String resourceName, String url, String userName, String password)
	{
		super(bundleName, createEmbeddedDataSource(resourceName, url, userName, password));
	}

	public EmbeddedDerbyBundle(String bundleName, String resourceName, String url, String userName, String password, PersistenceUnitInfoXml persistenceUnitInfo)
	{
		super(bundleName, createEmbeddedDataSource(resourceName, url, userName, password), persistenceUnitInfo);
	}

	private static DataSource createEmbeddedDataSource(String resourceName, String url, String userName, String password)
	{
		
		DatasourceProvider service = MobPlugin.getService(DatasourceProvider.class);
		return service.create(resourceName, EmbeddedDriver.class.getName(), url, userName, password);

	}
}

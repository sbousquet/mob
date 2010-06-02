package com.netappsid.mob.ejb3.osgi;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.netappsid.mob.ejb3.MobPlugin;
import com.netappsid.mob.ejb3.xml.PersistenceUnitInfoXml;

public abstract class OSGIEJB3Bundle
{
	private final String bundleName;
	private final DataSource datasource;
	private PersistenceUnitInfoXml persistenceUnitInfo;
	private EJB3Deployer deployer;
	
	public OSGIEJB3Bundle(String bundleName, DataSource datasource)
	{
		this.datasource = datasource;
		this.bundleName = bundleName;
		this.persistenceUnitInfo = configurePersistenceUnitInfoXml();
	}
	
	public OSGIEJB3Bundle(String bundleName, DataSource datasource, PersistenceUnitInfoXml persistenceUnitInfo)
	{
		this.datasource = datasource;
		this.bundleName = bundleName;
		this.persistenceUnitInfo = persistenceUnitInfo;
	}
	
	public final void deploy() throws NamingException
	{
		if (!isDeployed())
		{
			DeployOSGIEJB3Bundle.init();
			bindDataSource();
			
			if (deployer == null)
			{
				deployer = createEJB3Deployer();
				configureDeployer(deployer);
			}
			
			deployer.deploy();
		}
		else
		{
			throw new IllegalStateException("The bundle is already deployed.");
		}
	}
	
	public final void undeploy() throws NamingException
	{
		if (isDeployed())
		{
			deployer.undeploy();
			deployer = null;
		}
	}
	
	public final boolean isDeployed()
	{
		return deployer != null && deployer.isDeployed();
	}
	
	public final String getBundleName()
	{
		return bundleName;
	}
	
	public final <T> T lookup(String name) throws NamingException
	{
		if (isDeployed())
		{
			return (T) MobPlugin.getService(Context.class).lookup(bundleName + "/" + name);
		}
		else
		{
			throw new IllegalStateException("The bundle is not deployed.");
		}
	}
	
	private void bindDataSource() throws NamingException
	{
		MobPlugin.getService(Context.class).bind(persistenceUnitInfo.getJtaDatasoure(), datasource);
	}
	
	
	private EJB3Deployer createEJB3Deployer()
	{
		final EJB3Deployer deployer = new EJB3Deployer(DeployOSGIEJB3Bundle.getExecutorService(), bundleName);
		
		deployer.setClassLoader(getClass().getClassLoader());
		deployer.setPersistenceUnitInfoXml(persistenceUnitInfo);
		
		return deployer;
	}
	
	protected abstract PersistenceUnitInfoXml configurePersistenceUnitInfoXml();
	protected abstract void configureDeployer(EJB3Deployer deployer);
	
}

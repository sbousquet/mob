/**
 * 
 */
package com.netappsid.mob.datasource;

import javax.sql.DataSource;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.netappsid.mob.ejb3.DatasourceProvider;


/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class MobDatasourceProvider implements DatasourceProvider
{
	@Override
	public DataSource create(String uniqueRessourceName,String driverClassName,String jdbcUrl,String user,String password)
	{
		AtomikosNonXADataSourceBean ds = new AtomikosNonXADataSourceBean();
		ds.setUniqueResourceName(uniqueRessourceName);
		ds.setDriverClassName(driverClassName);
		ds.setUrl(jdbcUrl);
		ds.setUser(user);
		ds.setPassword(password);
		ds.setPoolSize(10);
		
		return ds;
	}
}

/**
 * 
 */
package com.netappsid.mob.ejb3;

import javax.sql.DataSource;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public interface DatasourceProvider
{

	/**
	 * @param uniqueRessourceName
	 * @param driverClassName
	 * @param jdbcUrl
	 * @param user
	 * @param password
	 * @return
	 */
	DataSource create(String uniqueRessourceName, String driverClassName, String jdbcUrl, String user, String password);

}

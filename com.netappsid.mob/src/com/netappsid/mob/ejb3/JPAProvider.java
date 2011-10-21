/**
 * 
 */
package com.netappsid.mob.ejb3;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public interface JPAProvider
{
	public void addAnnotatedClass(Class<?> entityClass);

	public void configure(PersistenceUnitInfo persistenceUnitInfo);

	public EntityManagerFactory buildEntityManagerFactory();

	public void addResource(String path, ClassLoader classLoader);

	public void addResource(String path);
}

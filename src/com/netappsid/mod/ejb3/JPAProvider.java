/**
 * 
 */
package com.netappsid.mod.ejb3;

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
}

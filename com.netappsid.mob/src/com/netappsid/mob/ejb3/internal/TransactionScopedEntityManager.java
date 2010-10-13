/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class TransactionScopedEntityManager implements EntityManager
{
	
	private final EJB3BundleUnit ejb3BundleUnit;

	/**
	 * 
	 */
	public TransactionScopedEntityManager(EJB3BundleUnit ejb3BundleUnit)
	{
		this.ejb3BundleUnit = ejb3BundleUnit;
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#clear()
	 */
	@Override
	public void clear()
	{
		ejb3BundleUnit.getManager().clear();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#close()
	 */
	@Override
	public void close()
	{
		ejb3BundleUnit.getManager().close();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object arg0)
	{
		return ejb3BundleUnit.getManager().contains(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String)
	 */
	@Override
	public Query createNamedQuery(String arg0)
	{
		return ejb3BundleUnit.getManager().createNamedQuery(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String)
	 */
	@Override
	public Query createNativeQuery(String arg0)
	{
		return ejb3BundleUnit.getManager().createNativeQuery(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.Class)
	 */
	@Override
	public Query createNativeQuery(String arg0, Class arg1)
	{
		return ejb3BundleUnit.getManager().createNativeQuery(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.String)
	 */
	@Override
	public Query createNativeQuery(String arg0, String arg1)
	{
		return ejb3BundleUnit.getManager().createNativeQuery(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#createQuery(java.lang.String)
	 */
	@Override
	public Query createQuery(String arg0)
	{
		return ejb3BundleUnit.getManager().createQuery(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T find(Class<T> arg0, Object arg1)
	{
		return ejb3BundleUnit.getManager().find(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#flush()
	 */
	@Override
	public void flush()
	{
		ejb3BundleUnit.getManager().flush();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getDelegate()
	 */
	@Override
	public Object getDelegate()
	{
		return ejb3BundleUnit.getManager().getDelegate();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getFlushMode()
	 */
	@Override
	public FlushModeType getFlushMode()
	{
		return ejb3BundleUnit.getManager().getFlushMode();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T getReference(Class<T> arg0, Object arg1)
	{
		return ejb3BundleUnit.getManager().getReference(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#getTransaction()
	 */
	@Override
	public EntityTransaction getTransaction()
	{
		return ejb3BundleUnit.getManager().getTransaction();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#isOpen()
	 */
	@Override
	public boolean isOpen()
	{
		return ejb3BundleUnit.getManager().isOpen();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#joinTransaction()
	 */
	@Override
	public void joinTransaction()
	{
		ejb3BundleUnit.getManager().joinTransaction();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#lock(java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public void lock(Object arg0, LockModeType arg1)
	{
		ejb3BundleUnit.getManager().lock(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#merge(java.lang.Object)
	 */
	@Override
	public <T> T merge(T arg0)
	{
		return ejb3BundleUnit.getManager().merge(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#persist(java.lang.Object)
	 */
	@Override
	public void persist(Object arg0)
	{
		ejb3BundleUnit.getManager().persist(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(Object arg0)
	{
		ejb3BundleUnit.getManager().refresh(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#remove(java.lang.Object)
	 */
	@Override
	public void remove(Object arg0)
	{
		ejb3BundleUnit.getManager().remove(arg0);
	}

	/* (non-Javadoc)
	 * @see javax.persistence.EntityManager#setFlushMode(javax.persistence.FlushModeType)
	 */
	@Override
	public void setFlushMode(FlushModeType arg0)
	{
		ejb3BundleUnit.getManager().setFlushMode(arg0);
	}

}

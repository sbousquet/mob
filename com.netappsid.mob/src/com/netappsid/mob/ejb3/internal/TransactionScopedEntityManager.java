/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

import com.netappsid.mob.ejb3.internal.interceptors.InterceptorHandler;
import com.netappsid.mob.ejb3.internal.interceptors.Interceptors;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#clear()
	 */
	@Override
	public void clear()
	{
		ejb3BundleUnit.getManager().clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#close()
	 */
	@Override
	public void close()
	{
		ejb3BundleUnit.getManager().close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object arg0)
	{
		return ejb3BundleUnit.getManager().contains(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String)
	 */
	@Override
	public Query createNamedQuery(String arg0)
	{
		return ejb3BundleUnit.getManager().createNamedQuery(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String)
	 */
	@Override
	public Query createNativeQuery(String arg0)
	{
		return ejb3BundleUnit.getManager().createNativeQuery(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.Class)
	 */
	@Override
	public Query createNativeQuery(String arg0, Class arg1)
	{
		return ejb3BundleUnit.getManager().createNativeQuery(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.String)
	 */
	@Override
	public Query createNativeQuery(String arg0, String arg1)
	{
		return ejb3BundleUnit.getManager().createNativeQuery(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#createQuery(java.lang.String)
	 */
	@Override
	public Query createQuery(String arg0)
	{
		return ejb3BundleUnit.getManager().createQuery(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T find(Class<T> arg0, Object arg1)
	{
		return ejb3BundleUnit.getManager().find(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#flush()
	 */
	@Override
	public void flush()
	{
		ejb3BundleUnit.getManager().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getDelegate()
	 */
	@Override
	public Object getDelegate()
	{
		return ejb3BundleUnit.getManager().getDelegate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getFlushMode()
	 */
	@Override
	public FlushModeType getFlushMode()
	{
		return ejb3BundleUnit.getManager().getFlushMode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T getReference(Class<T> arg0, Object arg1)
	{
		return ejb3BundleUnit.getManager().getReference(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#getTransaction()
	 */
	@Override
	public EntityTransaction getTransaction()
	{
		return ejb3BundleUnit.getManager().getTransaction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#isOpen()
	 */
	@Override
	public boolean isOpen()
	{
		return ejb3BundleUnit.getManager().isOpen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#joinTransaction()
	 */
	@Override
	public void joinTransaction()
	{
		ejb3BundleUnit.getManager().joinTransaction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#lock(java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public void lock(Object arg0, LockModeType arg1)
	{
		ejb3BundleUnit.getManager().lock(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#merge(java.lang.Object)
	 */
	@Override
	public <T> T merge(T arg0)
	{
		return ejb3BundleUnit.getManager().merge(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#persist(java.lang.Object)
	 */
	@Override
	public void persist(Object arg0)
	{
		ejb3BundleUnit.getManager().persist(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(Object arg0)
	{
		ejb3BundleUnit.getManager().refresh(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#remove(java.lang.Object)
	 */
	@Override
	public void remove(Object arg0)
	{
		ejb3BundleUnit.getManager().remove(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.persistence.EntityManager#setFlushMode(javax.persistence.FlushModeType)
	 */
	@Override
	public void setFlushMode(FlushModeType arg0)
	{
		ejb3BundleUnit.getManager().setFlushMode(arg0);
	}

	@Override
	public int hashCode()
	{
		return ejb3BundleUnit.hashCode();
	}

	public void addService(EJb3Service service)
	{
		ejb3BundleUnit.addService(service);
	}

	public StatelessPool getStatelessPool()
	{
		return ejb3BundleUnit.getStatelessPool();
	}

	public void removeService(EJb3Service ejb3Service)
	{
		ejb3BundleUnit.removeService(ejb3Service);
	}

	public EntityManager getManager()
	{
		return ejb3BundleUnit.getManager();
	}

	@Override
	public boolean equals(Object obj)
	{
		return ejb3BundleUnit.equals(obj);
	}

	public void inject(Object obj)
	{
		ejb3BundleUnit.inject(obj);
	}

	public String getName()
	{
		return ejb3BundleUnit.getName();
	}

	public void setName(String name)
	{
		ejb3BundleUnit.setName(name);
	}

	public EntityManagerFactory getManagerFactory()
	{
		return ejb3BundleUnit.getManagerFactory();
	}

	public void setManagerFactory(EntityManagerFactory managerFactory)
	{
		ejb3BundleUnit.setManagerFactory(managerFactory);
	}

	public List<String> getServicesNames()
	{
		return ejb3BundleUnit.getServicesNames();
	}

	public List<InterceptorHandler> getInterceptors(String serviceName)
	{
		return ejb3BundleUnit.getInterceptors(serviceName);
	}

	public void setInterceptors(Interceptors interceptors)
	{
		ejb3BundleUnit.setInterceptors(interceptors);
	}

	public <T> T create(Class<T> toCreate) throws Exception
	{
		return ejb3BundleUnit.create(toCreate);
	}

	@Override
	public String toString()
	{
		return ejb3BundleUnit.toString();
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String arg0, Class<T> arg1)
	{
		return getManager().createNamedQuery(arg0, arg1);
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> arg0)
	{
		return getManager().createQuery(arg0);
	}

	@Override
	public <T> TypedQuery<T> createQuery(String arg0, Class<T> arg1)
	{
		return getManager().createQuery(arg0, arg1);
	}

	@Override
	public void detach(Object arg0)
	{
		getManager().detach(arg0);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2, Map<String, Object> arg3)
	{
		return getManager().find(arg0, arg1, arg2, arg3);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2)
	{
		return getManager().find(arg0, arg1, arg2);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, Map<String, Object> arg2)
	{
		return getManager().find(arg0, arg1, arg2);
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder()
	{
		return getManager().getCriteriaBuilder();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory()
	{
		return getManager().getEntityManagerFactory();
	}

	@Override
	public LockModeType getLockMode(Object arg0)
	{
		return getManager().getLockMode(arg0);
	}

	@Override
	public Metamodel getMetamodel()
	{
		return getManager().getMetamodel();
	}

	@Override
	public Map<String, Object> getProperties()
	{
		return getManager().getProperties();
	}

	@Override
	public void lock(Object arg0, LockModeType arg1, Map<String, Object> arg2)
	{
		getManager().lock(arg0, arg1, arg2);
	}

	@Override
	public void refresh(Object arg0, LockModeType arg1, Map<String, Object> arg2)
	{
		getManager().refresh(arg0, arg1, arg2);
	}

	@Override
	public void refresh(Object arg0, LockModeType arg1)
	{
		getManager().refresh(arg0, arg1);
	}

	@Override
	public void refresh(Object arg0, Map<String, Object> arg1)
	{
		getManager().refresh(arg0, arg1);
	}

	@Override
	public void setProperty(String arg0, Object arg1)
	{
		getManager().setProperty(arg0, arg1);
	}

	@Override
	public <T> T unwrap(Class<T> arg0)
	{
		return getManager().unwrap(arg0);
	}

}

package com.netappsid.ejb3.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.hibernate.Interceptor;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.event.DeleteEventListener;
import org.hibernate.event.EventListeners;
import org.hibernate.event.MergeEventListener;
import org.hibernate.event.PersistEventListener;
import org.hibernate.event.PostCollectionUpdateEventListener;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.event.ReplicateEventListener;

import com.netappsid.mob.ejb3.JPAProvider;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 */
public class HibernateJPAProvider implements JPAProvider
{
	private static final Logger logger = Logger.getLogger(HibernateJPAProvider.class);
	private Ejb3Configuration cfg;

	public HibernateJPAProvider()
	{
		cfg = new Ejb3Configuration();
	}

	public void addAnnotatedClass(Class<?> entityClass)
	{
		cfg.addAnnotatedClass(entityClass);
	}

	public EntityManagerFactory buildEntityManagerFactory()
	{
		return cfg.buildEntityManagerFactory();
	}

	public void configure(PersistenceUnitInfo persistenceUnitInfo)
	{
		configureEventListeners();
		configureSessionFactoryInterceptor();

		Properties properties = persistenceUnitInfo.getProperties();
		properties.put("hibernate.transaction.factory_class", "org.hibernate.transaction.JTATransactionFactory");
		properties.put("hibernate.transaction.manager_lookup_class", "com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup");
		properties.put("hibernate.archive.autodetection", "false");
		properties.put("jta.UserTransaction", "java:/UserTransaction");

		cfg.configure(persistenceUnitInfo, properties);

	}

	private void configureEventListeners()
	{
		final EventListeners eventListeners = cfg.getEventListeners();

		eventListeners.setPersistEventListeners(mergeArrays(eventListeners.getPersistEventListeners(), getExtensionsPersistEventListeners()));
		eventListeners.setMergeEventListeners(mergeArrays(eventListeners.getMergeEventListeners(), getExtensionsMergeEventListeners()));
		eventListeners.setDeleteEventListeners(mergeArrays(eventListeners.getDeleteEventListeners(), getExtensionsDeleteEventListeners()));
		eventListeners.setReplicateEventListeners(mergeArrays(eventListeners.getReplicateEventListeners(), getExtensionsReplicateEventListeners()));
		eventListeners.setPostInsertEventListeners(mergeArrays(eventListeners.getPostInsertEventListeners(), getExtensionsPostInsertEventListeners()));
		eventListeners.setPostUpdateEventListeners(mergeArrays(eventListeners.getPostUpdateEventListeners(), getExtensionsPostUpdateEventListeners()));
		eventListeners.setPostDeleteEventListeners(mergeArrays(eventListeners.getPostDeleteEventListeners(), getExtensionsPostDeleteEventListeners()));
		eventListeners.setPostCollectionUpdateEventListeners(mergeArrays(eventListeners.getPostCollectionUpdateEventListeners(),
				getExtensionPostCollectionUpdateEventListeners()));
	}

	private void configureSessionFactoryInterceptor()
	{
		final Set<Interceptor> interceptors = getExecutableExtensions("com.netappsid.ejb3.hibernate.sessionfactory.interceptor", "class");

		if (!interceptors.isEmpty())
		{
			cfg.setInterceptor(interceptors.iterator().next());
		}
	}

	private PersistEventListener[] getExtensionsPersistEventListeners()
	{
		return getExecutableExtensions("com.netappsid.ejb3.hibernate.event.persist", "class").toArray(new PersistEventListener[0]);
	}

	private MergeEventListener[] getExtensionsMergeEventListeners()
	{
		return getExecutableExtensions("com.netappsid.ejb3.hibernate.event.merge", "class").toArray(new MergeEventListener[0]);
	}

	private DeleteEventListener[] getExtensionsDeleteEventListeners()
	{
		return getExecutableExtensions("com.netappsid.ejb3.hibernate.event.delete", "class").toArray(new DeleteEventListener[0]);
	}

	private ReplicateEventListener[] getExtensionsReplicateEventListeners()
	{
		return getExecutableExtensions("com.netappsid.ejb3.hibernate.event.replicate", "class").toArray(new ReplicateEventListener[0]);
	}

	private PostInsertEventListener[] getExtensionsPostInsertEventListeners()
	{
		return getExecutableExtensions("com.netappsid.ejb3.hibernate.events", "postInsertEventListener", "class").toArray(new PostInsertEventListener[0]);
	}

	private PostUpdateEventListener[] getExtensionsPostUpdateEventListeners()
	{
		return getExecutableExtensions("com.netappsid.ejb3.hibernate.events", "postUpdateEventListener", "class").toArray(new PostUpdateEventListener[0]);
	}

	private PostDeleteEventListener[] getExtensionsPostDeleteEventListeners()
	{
		return getExecutableExtensions("com.netappsid.ejb3.hibernate.events", "postDeleteEventListener", "class").toArray(new PostDeleteEventListener[0]);
	}

	private PostCollectionUpdateEventListener[] getExtensionPostCollectionUpdateEventListeners()
	{
		return getExecutableExtensions("com.netappsid.ejb3.hibernate.events", "postCollectionUpdateEventListener", "class").toArray(
				new PostCollectionUpdateEventListener[0]);
	}

	private static <T> Set<T> getExecutableExtensions(String extensionPointID, String propertyName)
	{
		final Set<T> executableExtensions = new HashSet<T>();

		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPointID))
		{
			try
			{
				executableExtensions.add((T) configurationElement.createExecutableExtension(propertyName));
			}
			catch (CoreException e)
			{
				logger.error(e, e);
			}
		}

		return executableExtensions;
	}

	private static <T> Set<T> getExecutableExtensions(String extensionPointID, String configurationElementName, String propertyName)
	{
		final Set<T> executableExtensions = new HashSet<T>();

		for (IConfigurationElement element : Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPointID))
		{
			if (element.getName().equals(configurationElementName))
			{
				try
				{
					executableExtensions.add((T) element.createExecutableExtension(propertyName));
				}
				catch (CoreException e)
				{
					logger.error(e, e);
				}
			}
		}

		return executableExtensions;
	}

	private static <T> T[] mergeArrays(T[] array1, T[] array2)
	{
		final int newLength = array1.length + array2.length;
		final List<T> merged = new ArrayList<T>(newLength);

		for (int i = 0; i < newLength; i++)
		{
			merged.add(i < array1.length ? array1[i] : array2[i - array1.length]);
		}

		return merged.toArray(array1);
	}
}

/**
 * 
 */
package com.netappsid.mob.ejb3.xml;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Multimap;
import com.netappsid.mob.ejb3.MobPlugin;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.2 $
 */
public class PersistenceUnitInfoXml implements PersistenceUnitInfo
{
	private static Logger logger = LoggerFactory.getLogger(PersistenceUnitInfoXml.class);

	private ClassLoader classLoader;
	private String persistenceUnitName;
	private PersistenceUnitTransactionType transactionType;
	private String jtaDatasoure;
	private Properties properties = new Properties();
	private String persistenceProviderClassName;

	private final Context context;
	private final PersistenceUnitUtils persistenceUnitUtils;

	public PersistenceUnitInfoXml(Context context, PersistenceUnitUtils persistenceUnitUtils)
	{
		this.context = context;
		this.persistenceUnitUtils = persistenceUnitUtils;
	}

	public void fromInputStream(InputStream inputStream) throws DocumentException
	{
		fromInputStream(inputStream, persistenceUnitUtils.getXsltResourcesByPersistenceUnit());
	}

	public void fromInputStream(InputStream inputStream, Multimap<String, URL> xsltByUnitName) throws DocumentException
	{
		Document document = new SAXReader().read(inputStream);

		persistenceUnitName = document.getRootElement().element("persistence-unit").attributeValue("name");

		if (xsltByUnitName != null)
		{
			Collection<URL> xslt = xsltByUnitName.get(persistenceUnitName);

			try
			{
				if (xslt != null && !xslt.isEmpty())
				{
					document = persistenceUnitUtils.applyXslt(document, xslt.toArray(new URL[0]));
				}
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}

		}

		Element persistenceUnit = document.getRootElement().element("persistence-unit");

		String transaction = persistenceUnit.attributeValue("transaction-type");

		if (transaction.equals("JTA"))
		{
			transactionType = PersistenceUnitTransactionType.JTA;
		}
		else if (transaction.equals("RESOURCE_LOCAL"))
		{
			transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
		}

		persistenceProviderClassName = persistenceUnit.elementText("provider");
		jtaDatasoure = persistenceUnit.elementText("jta-data-source");

		// properties
		Element element = persistenceUnit.element("properties");

		Iterator<Element> iterator = element.elementIterator();
		while (iterator.hasNext())
		{
			Element element2 = (Element) iterator.next();
			properties.put(element2.attributeValue("name"), element2.attributeValue("value"));
		}
	}

	/**
	 * @param arg0
	 * @see javax.persistence.spi.PersistenceUnitInfo#addTransformer(javax.persistence.spi.ClassTransformer)
	 */
	@Override
	public void addTransformer(ClassTransformer arg0)
	{}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#excludeUnlistedClasses()
	 */
	@Override
	public boolean excludeUnlistedClasses()
	{
		return false;
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getClassLoader()
	 */
	@Override
	public ClassLoader getClassLoader()
	{
		return classLoader;
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getJarFileUrls()
	 */
	@Override
	public List<URL> getJarFileUrls()
	{
		return new ArrayList<URL>();
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getJtaDataSource()
	 */
	@Override
	public DataSource getJtaDataSource()
	{
		try
		{
			return (DataSource) context.lookup(jtaDatasoure);
		}
		catch (NamingException e)
		{
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getManagedClassNames()
	 */
	@Override
	public List<String> getManagedClassNames()
	{
		return new ArrayList<String>();
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getMappingFileNames()
	 */
	@Override
	public List<String> getMappingFileNames()
	{
		return new ArrayList<String>();
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getNewTempClassLoader()
	 */
	@Override
	public ClassLoader getNewTempClassLoader()
	{
		return null;
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getNonJtaDataSource()
	 */
	@Override
	public DataSource getNonJtaDataSource()
	{
		return null;
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getPersistenceProviderClassName()
	 */
	@Override
	public String getPersistenceProviderClassName()
	{
		return persistenceProviderClassName;
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getPersistenceUnitName()
	 */
	@Override
	public String getPersistenceUnitName()
	{
		return persistenceUnitName;
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getPersistenceUnitRootUrl()
	 */
	@Override
	public URL getPersistenceUnitRootUrl()
	{
		return null;
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getProperties()
	 */
	@Override
	public Properties getProperties()
	{
		return properties;
	}

	/**
	 * @return
	 * @see javax.persistence.spi.PersistenceUnitInfo#getTransactionType()
	 */
	@Override
	public PersistenceUnitTransactionType getTransactionType()
	{
		return transactionType;
	}

	/**
	 * @return Returns the jtaDatasoure.
	 */
	public String getJtaDatasoure()
	{
		return jtaDatasoure;
	}

	/**
	 * @param jtaDatasoure
	 *            The jtaDatasoure to set.
	 */
	public void setJtaDatasoure(String jtaDatasoure)
	{
		this.jtaDatasoure = jtaDatasoure;
	}

	/**
	 * @param classLoader
	 *            The classLoader to set.
	 */
	public void setClassLoader(ClassLoader classLoader)
	{
		this.classLoader = classLoader;
	}

	/**
	 * @param persistenceUnitName
	 *            The persistenceUnitName to set.
	 */
	public void setPersistenceUnitName(String persistenceUnitName)
	{
		this.persistenceUnitName = persistenceUnitName;
	}

	/**
	 * @param transactionType
	 *            The transactionType to set.
	 */
	public void setTransactionType(PersistenceUnitTransactionType transactionType)
	{
		this.transactionType = transactionType;
	}

	public void setTransactionType(String transactionType)
	{
		if ("JTA".equalsIgnoreCase(transactionType))
		{
			this.transactionType = PersistenceUnitTransactionType.JTA;
		}
		else if ("RESOURCE_LOCAL".equalsIgnoreCase(transactionType))
		{
			this.transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
		}
	}

	/**
	 * @param properties
	 *            The properties to set.
	 */
	public void setProperties(Properties properties)
	{
		this.properties = properties;
	}

	/**
	 * @param persistenceProviderClassName
	 *            The persistenceProviderClassName to set.
	 */
	public void setPersistenceProviderClassName(String persistenceProviderClassName)
	{
		this.persistenceProviderClassName = persistenceProviderClassName;
	}

}

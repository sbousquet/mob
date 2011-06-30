package com.netappsid.mob.ejb3.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.spi.RegistryContributor;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Multimap;
import com.netappsid.mob.ejb3.xml.PersistenceUnitUtils;

public class PersistenceUnitUtilsTest
{

	@Test
	public void testXsltExtention()
	{
		PersistenceUnitUtils persistenceUnitUtils = getPersistenceUnitUtils();

		Multimap<String, URL> xsltResourcesByPersistenceUnit = persistenceUnitUtils.getXsltResourcesByPersistenceUnit();

		assertEquals(1, xsltResourcesByPersistenceUnit.size());
		Collection<URL> collection = xsltResourcesByPersistenceUnit.get("erp");

		assertFalse(collection.isEmpty());

		URL next = collection.iterator().next();

		assertEquals("test.xslt", next.getPath().substring(next.getPath().lastIndexOf('/') + 1));

	}

	@Test
	public void testXSLTModification() throws IOException, TransformerException, DocumentException
	{
		Document document = new SAXReader().read(PersistenceUnitUtilsTest.class.getResourceAsStream("persistence.xml"));

		PersistenceUnitUtils persistenceUnitUtils = getPersistenceUnitUtils();
		Document applyXslt = persistenceUnitUtils.applyXslt(document, PersistenceUnitUtilsTest.class.getResource("test.xslt"));

		assertFalse(document.equals(applyXslt));

		Element element = applyXslt.getRootElement().element("persistence-unit").element("properties").element("test");

		assertEquals("test", element.getName());
		assertEquals("untest", element.attributeValue("name"));
		assertEquals("mavaleur", element.attributeValue("value"));
	}

	private PersistenceUnitUtils getPersistenceUnitUtils() throws TransformerFactoryConfigurationError
	{
		IExtensionRegistry extensionRegistry = new ExtensionRegistryAdapter()
			{
				@Override
				public IConfigurationElement[] getConfigurationElementsFor(String extensionPointId)
				{
					IConfigurationElement configurationElementAdapter = new ConfigurationElementAdapter()
						{
							@Override
							public IContributor getContributor() throws InvalidRegistryObjectException
							{
								return new RegistryContributor(Integer.toString(1), null, Integer.toString(1), null);
							}

							@Override
							public String getAttribute(String name) throws InvalidRegistryObjectException
							{
								if (name.equals("persistenceUnit"))
								{
									return "erp";
								}
								else if (name.equals("xslt"))
								{
									return "test.xslt";
								}
								return null;
							}
						};

					return new IConfigurationElement[] { configurationElementAdapter };
				}
			};

		BundleContext bundleContext = mock(BundleContext.class);
		Bundle bundle = mock(Bundle.class);
		when(bundle.getResource(anyString())).thenAnswer(new Answer<URL>()
			{

				@Override
				public URL answer(InvocationOnMock invocationOnMock) throws Throwable
				{
					String name = (String) invocationOnMock.getArguments()[0];
					return PersistenceUnitUtilsTest.class.getResource(name);
				}
			});
		when(bundleContext.getBundle(anyLong())).thenReturn(bundle);

		PersistenceUnitUtils persistenceUnitUtils = new PersistenceUnitUtils(bundleContext, extensionRegistry, TransformerFactory.newInstance());
		return persistenceUnitUtils;
	}
}

package com.netappsid.mob.ejb3.xml;

import java.io.IOException;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.spi.RegistryContributor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class PersistenceUnitUtils
{
	public static Multimap<String, URL> getXsltResourcesByPersistenceUnit(IExtensionRegistry extensionRegistry, BundleContext context)
	{
		Multimap<String, URL> xsltByUnit = ArrayListMultimap.create();

		IConfigurationElement[] xsltExtentions = extensionRegistry.getConfigurationElementsFor("com.netappsid.ejb3.persistence.xslt");

		for (IConfigurationElement modif : xsltExtentions)
		{
			Bundle bundle = context.getBundle(Long.parseLong(((RegistryContributor) modif.getContributor()).getId()));
			String name = modif.getAttribute("persistenceUnit");
			URL resource = bundle.getResource(modif.getAttribute("xslt"));
			xsltByUnit.put(name, resource);
		}

		return xsltByUnit;

	}

	public static Document applyXslt(Document document, URL... xslt) throws IOException, TransformerException
	{
		Document temp = document;

		for (URL url : xslt)
		{
			// load the transformer using JAXP
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(url.openStream()));

			// now lets style the given document
			DocumentSource source = new DocumentSource(temp);
			DocumentResult result = new DocumentResult();
			transformer.transform(source, result);

			// return the transformed document
			temp = result.getDocument();
		}

		return temp;
	}

}

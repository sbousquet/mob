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
	private final IExtensionRegistry extensionRegistry;
	private final BundleContext context;
	private final TransformerFactory transformerFactory;

	public PersistenceUnitUtils(BundleContext context,IExtensionRegistry extensionRegistry,TransformerFactory transformerFactory)
	{
		this.context = context;
		this.extensionRegistry = extensionRegistry;
		this.transformerFactory = transformerFactory;
	}
	
	public Multimap<String, URL> getXsltResourcesByPersistenceUnit()
	{
		Multimap<String, URL> xsltByUnit = ArrayListMultimap.create();

		IConfigurationElement[] xsltExtentions = extensionRegistry.getConfigurationElementsFor("com.netappsid.mob.ejb3.persistence.xslt");

		for (IConfigurationElement modif : xsltExtentions)
		{
			Bundle bundle = context.getBundle(Long.parseLong(((RegistryContributor) modif.getContributor()).getId()));
			String name = modif.getAttribute("persistenceUnit");
			URL resource = bundle.getResource(modif.getAttribute("xslt"));
			xsltByUnit.put(name, resource);
		}

		return xsltByUnit;

	}

	public Document applyXslt(Document document, URL... xslt) throws IOException, TransformerException
	{
		Document temp = document;

		for (URL url : xslt)
		{
			Transformer transformer = transformerFactory.newTransformer(new StreamSource(url.openStream()));

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

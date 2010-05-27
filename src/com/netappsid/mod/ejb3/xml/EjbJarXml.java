package com.netappsid.mod.ejb3.xml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class EjbJarXml
{

	private Map<String, String> interceptorsByEjbName = new LinkedHashMap<String, String>();

	public void fromInputStream(InputStream inputStream) throws DocumentException
	{
		Document document = new SAXReader().read(inputStream);
		Element assemblyDecriptor = document.getRootElement().element("assembly-descriptor");

		if (assemblyDecriptor != null)
		{
			List<Element> elements = assemblyDecriptor.elements("interceptor-binding");

			for (Element interceptorBinding : elements)
			{
				String ejbName = interceptorBinding.element("ejb-name").getText();
				String interceptorClass = interceptorBinding.element("interceptor-class").getText();

				interceptorsByEjbName.put(ejbName, interceptorClass);
			}

		}

	}

	public Map<String, String> getInterceptorsByEjbName()
	{
		return interceptorsByEjbName;
	}
}

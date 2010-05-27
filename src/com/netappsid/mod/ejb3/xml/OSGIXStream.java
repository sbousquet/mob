package com.netappsid.mod.ejb3.xml;

import com.netappsid.mod.ejb3.io.OSGIClassResolver;
import com.netappsid.mod.ejb3.io.OSGIClassVersionStringProvider;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class OSGIXStream extends XStream
{
	private OSGIClassVersionStringProvider classVersionStringProvider = new OSGIClassVersionStringProvider();
	private OSGIClassResolver classResolver = new OSGIClassResolver();

	@Override
	protected MapperWrapper wrapMapper(MapperWrapper next)
	{
		return new MapperWrapper(next)
			{

				@Override
				public String serializedClass(Class type)
				{
					String className = type.getName();

					String versionString = classVersionStringProvider.getClassVersionString(type, "-");

					if (versionString != null)
					{
						className = className + "_" + versionString;
					}

					return className;
				}

				@Override
				public Class realClass(String elementName)
				{
					if (elementName.indexOf('_') != -1)
					{
						String[] split = elementName.split("_");
						try
						{
							return classResolver.resolveClass(split[0], split[1], "-");
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}
						return null;
					}
					else
					{
						return super.realClass(elementName);
					}

				}

				@Override
				public boolean shouldSerializeMember(Class definedIn, String fieldName)
				{
					return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
				}

			};
	}
}

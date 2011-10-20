package com.netappsid.mob.ejb3.xml;

public class OrmXML
{
	private final ClassLoader classLoader;
	private final String resourcePath;

	public OrmXML(ClassLoader classLoader, String resource)
	{
		this.classLoader = classLoader;
		this.resourcePath = resource;
	}

	public ClassLoader getClassLoader()
	{
		return classLoader;
	}

	public String getResource()
	{
		return resourcePath;
	}

}

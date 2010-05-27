package com.netappsid.mod.ejb3.naming;

import java.util.Properties;

public class InitialContextUtils
{
	private static Properties defaultContextProperties;
	

	public static void bindToSystemProperties(Properties properties)
	{
		for (Object key : properties.keySet())
		{
			System.setProperty((String)key, (String)properties.get(key));
		}
	}
	/**
	 * @return Returns the defaultContextProperties.
	 */
	public static Properties getDefaultContextProperties()
	{
		return defaultContextProperties;
	}

	/**
	 * @param defaultContextProperties The defaultContextProperties to set.
	 */
	public static void setDefaultContextProperties(Properties defaultContextProperties)
	{
		if(defaultContextProperties!=null)
		{
			for (Object key : defaultContextProperties.keySet())
			{
				System.setProperty((String)key, defaultContextProperties.getProperty((String)key));
			}
		}
		
		
		InitialContextUtils.defaultContextProperties = defaultContextProperties;
	}

}

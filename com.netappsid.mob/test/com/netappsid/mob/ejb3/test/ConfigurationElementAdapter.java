/**
 * 
 */
package com.netappsid.mob.ejb3.test;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.InvalidRegistryObjectException;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class ConfigurationElementAdapter implements IConfigurationElement
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#createExecutableExtension(java.lang.String)
	 */

	public Object createExecutableExtension(String propertyName) throws CoreException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getAttribute(java.lang.String)
	 */

	public String getAttribute(String name) throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getAttribute(java.lang.String, java.lang.String)
	 */

	public String getAttribute(String attrName, String locale) throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getAttributeAsIs(java.lang.String)
	 */

	public String getAttributeAsIs(String name) throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getAttributeNames()
	 */

	public String[] getAttributeNames() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getChildren()
	 */

	public IConfigurationElement[] getChildren() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getChildren(java.lang.String)
	 */

	public IConfigurationElement[] getChildren(String name) throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getDeclaringExtension()
	 */

	public IExtension getDeclaringExtension() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getName()
	 */

	public String getName() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getParent()
	 */

	public Object getParent() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getValue()
	 */

	public String getValue() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getValue(java.lang.String)
	 */

	public String getValue(String locale) throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getValueAsIs()
	 */
	public String getValueAsIs() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getNamespace()
	 */
	public String getNamespace() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getNamespaceIdentifier()
	 */

	public String getNamespaceIdentifier() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#getContributor()
	 */
	public IContributor getContributor() throws InvalidRegistryObjectException
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IConfigurationElement#isValid()
	 */
	public boolean isValid()
	{
		return false;
	}

}

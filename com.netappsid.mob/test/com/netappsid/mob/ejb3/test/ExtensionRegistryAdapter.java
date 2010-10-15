/**
 * 
 */
package com.netappsid.mob.ejb3.test;

import java.io.InputStream;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.IRegistryEventListener;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class ExtensionRegistryAdapter implements IExtensionRegistry
{

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#addRegistryChangeListener(org.eclipse.core.runtime.IRegistryChangeListener, java.lang.String)
	 */
	
	public void addRegistryChangeListener(IRegistryChangeListener listener, String namespace)
	{}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#addRegistryChangeListener(org.eclipse.core.runtime.IRegistryChangeListener)
	 */
	
	public void addRegistryChangeListener(IRegistryChangeListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getConfigurationElementsFor(java.lang.String)
	 */
	
	public IConfigurationElement[] getConfigurationElementsFor(String extensionPointId)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getConfigurationElementsFor(java.lang.String, java.lang.String)
	 */
	
	public IConfigurationElement[] getConfigurationElementsFor(String namespace, String extensionPointName)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getConfigurationElementsFor(java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public IConfigurationElement[] getConfigurationElementsFor(String namespace, String extensionPointName, String extensionId)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtension(java.lang.String)
	 */
	
	public IExtension getExtension(String extensionId)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtension(java.lang.String, java.lang.String)
	 */
	
	public IExtension getExtension(String extensionPointId, String extensionId)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtension(java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public IExtension getExtension(String namespace, String extensionPointName, String extensionId)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtensionPoint(java.lang.String)
	 */
	
	public IExtensionPoint getExtensionPoint(String extensionPointId)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtensionPoint(java.lang.String, java.lang.String)
	 */
	
	public IExtensionPoint getExtensionPoint(String namespace, String extensionPointName)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtensionPoints()
	 */
	
	public IExtensionPoint[] getExtensionPoints()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtensionPoints(java.lang.String)
	 */
	
	public IExtensionPoint[] getExtensionPoints(String namespace)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtensionPoints(org.eclipse.core.runtime.IContributor)
	 */
	
	public IExtensionPoint[] getExtensionPoints(IContributor contributor)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtensions(java.lang.String)
	 */
	
	public IExtension[] getExtensions(String namespace)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getExtensions(org.eclipse.core.runtime.IContributor)
	 */
	
	public IExtension[] getExtensions(IContributor contributor)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#getNamespaces()
	 */
	
	public String[] getNamespaces()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#removeRegistryChangeListener(org.eclipse.core.runtime.IRegistryChangeListener)
	 */
	
	public void removeRegistryChangeListener(IRegistryChangeListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#addContribution(java.io.InputStream, org.eclipse.core.runtime.IContributor, boolean, java.lang.String, java.util.ResourceBundle, java.lang.Object)
	 */
	
	public boolean addContribution(InputStream is, IContributor contributor, boolean persist, String name, ResourceBundle translationBundle, Object token)
			throws IllegalArgumentException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#removeExtension(org.eclipse.core.runtime.IExtension, java.lang.Object)
	 */
	
	public boolean removeExtension(IExtension extension, Object token) throws IllegalArgumentException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#removeExtensionPoint(org.eclipse.core.runtime.IExtensionPoint, java.lang.Object)
	 */
	
	public boolean removeExtensionPoint(IExtensionPoint extensionPoint, Object token) throws IllegalArgumentException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#stop(java.lang.Object)
	 */
	
	public void stop(Object token) throws IllegalArgumentException
	{}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#addListener(org.eclipse.core.runtime.IRegistryEventListener)
	 */
	
	public void addListener(IRegistryEventListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#addListener(org.eclipse.core.runtime.IRegistryEventListener, java.lang.String)
	 */
	
	public void addListener(IRegistryEventListener listener, String extensionPointId)
	{}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#removeListener(org.eclipse.core.runtime.IRegistryEventListener)
	 */
	
	public void removeListener(IRegistryEventListener listener)
	{}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExtensionRegistry#isMultiLanguage()
	 */
	
	public boolean isMultiLanguage()
	{
		return false;
	}

}

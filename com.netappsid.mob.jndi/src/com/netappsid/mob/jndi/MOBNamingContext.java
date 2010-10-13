/**
 * 
 */
package com.netappsid.mob.jndi;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.naming.NamingContext;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class MOBNamingContext implements Context
{

	private static final String CLIENT = "client";
	private static final String NAID_MODE = "naid.mode";

	private Context delegate;
	
	/**
	 * @throws NamingException 
	 * 
	 */
	public MOBNamingContext() throws NamingException
	{
		if(CLIENT.equals(System.getProperty(NAID_MODE)))
		{
			delegate = new ClientNamingContext();
		}
		else
		{
			delegate = new NamingContext();
		}
	}
	

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#lookup(javax.naming.Name)
	 */
	public Object lookup(Name name) throws NamingException
	{
		return delegate.lookup(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#lookup(java.lang.String)
	 */
	public Object lookup(String name) throws NamingException
	{
		return delegate.lookup(name);
	}

	/**
	 * @param name
	 * @param obj
	 * @throws NamingException
	 * @see javax.naming.Context#bind(javax.naming.Name, java.lang.Object)
	 */
	public void bind(Name name, Object obj) throws NamingException
	{
		delegate.bind(name, obj);
	}

	/**
	 * @param name
	 * @param obj
	 * @throws NamingException
	 * @see javax.naming.Context#bind(java.lang.String, java.lang.Object)
	 */
	public void bind(String name, Object obj) throws NamingException
	{
		delegate.bind(name, obj);
	}

	/**
	 * @param name
	 * @param obj
	 * @throws NamingException
	 * @see javax.naming.Context#rebind(javax.naming.Name, java.lang.Object)
	 */
	public void rebind(Name name, Object obj) throws NamingException
	{
		delegate.rebind(name, obj);
	}

	/**
	 * @param name
	 * @param obj
	 * @throws NamingException
	 * @see javax.naming.Context#rebind(java.lang.String, java.lang.Object)
	 */
	public void rebind(String name, Object obj) throws NamingException
	{
		delegate.rebind(name, obj);
	}

	/**
	 * @param name
	 * @throws NamingException
	 * @see javax.naming.Context#unbind(javax.naming.Name)
	 */
	public void unbind(Name name) throws NamingException
	{
		delegate.unbind(name);
	}

	/**
	 * @param name
	 * @throws NamingException
	 * @see javax.naming.Context#unbind(java.lang.String)
	 */
	public void unbind(String name) throws NamingException
	{
		delegate.unbind(name);
	}

	/**
	 * @param oldName
	 * @param newName
	 * @throws NamingException
	 * @see javax.naming.Context#rename(javax.naming.Name, javax.naming.Name)
	 */
	public void rename(Name oldName, Name newName) throws NamingException
	{
		delegate.rename(oldName, newName);
	}

	/**
	 * @param oldName
	 * @param newName
	 * @throws NamingException
	 * @see javax.naming.Context#rename(java.lang.String, java.lang.String)
	 */
	public void rename(String oldName, String newName) throws NamingException
	{
		delegate.rename(oldName, newName);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#list(javax.naming.Name)
	 */
	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException
	{
		return delegate.list(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#list(java.lang.String)
	 */
	public NamingEnumeration<NameClassPair> list(String name) throws NamingException
	{
		return delegate.list(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#listBindings(javax.naming.Name)
	 */
	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException
	{
		return delegate.listBindings(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#listBindings(java.lang.String)
	 */
	public NamingEnumeration<Binding> listBindings(String name) throws NamingException
	{
		return delegate.listBindings(name);
	}

	/**
	 * @param name
	 * @throws NamingException
	 * @see javax.naming.Context#destroySubcontext(javax.naming.Name)
	 */
	public void destroySubcontext(Name name) throws NamingException
	{
		delegate.destroySubcontext(name);
	}

	/**
	 * @param name
	 * @throws NamingException
	 * @see javax.naming.Context#destroySubcontext(java.lang.String)
	 */
	public void destroySubcontext(String name) throws NamingException
	{
		delegate.destroySubcontext(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#createSubcontext(javax.naming.Name)
	 */
	public Context createSubcontext(Name name) throws NamingException
	{
		return delegate.createSubcontext(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#createSubcontext(java.lang.String)
	 */
	public Context createSubcontext(String name) throws NamingException
	{
		return delegate.createSubcontext(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#lookupLink(javax.naming.Name)
	 */
	public Object lookupLink(Name name) throws NamingException
	{
		return delegate.lookupLink(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#lookupLink(java.lang.String)
	 */
	public Object lookupLink(String name) throws NamingException
	{
		return delegate.lookupLink(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#getNameParser(javax.naming.Name)
	 */
	public NameParser getNameParser(Name name) throws NamingException
	{
		return delegate.getNameParser(name);
	}

	/**
	 * @param name
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#getNameParser(java.lang.String)
	 */
	public NameParser getNameParser(String name) throws NamingException
	{
		return delegate.getNameParser(name);
	}

	/**
	 * @param name
	 * @param prefix
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#composeName(javax.naming.Name, javax.naming.Name)
	 */
	public Name composeName(Name name, Name prefix) throws NamingException
	{
		return delegate.composeName(name, prefix);
	}

	/**
	 * @param name
	 * @param prefix
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#composeName(java.lang.String, java.lang.String)
	 */
	public String composeName(String name, String prefix) throws NamingException
	{
		return delegate.composeName(name, prefix);
	}

	/**
	 * @param propName
	 * @param propVal
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#addToEnvironment(java.lang.String, java.lang.Object)
	 */
	public Object addToEnvironment(String propName, Object propVal) throws NamingException
	{
		return delegate.addToEnvironment(propName, propVal);
	}

	/**
	 * @param propName
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#removeFromEnvironment(java.lang.String)
	 */
	public Object removeFromEnvironment(String propName) throws NamingException
	{
		return delegate.removeFromEnvironment(propName);
	}

	/**
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#getEnvironment()
	 */
	public Hashtable<?, ?> getEnvironment() throws NamingException
	{
		return delegate.getEnvironment();
	}

	/**
	 * @throws NamingException
	 * @see javax.naming.Context#close()
	 */
	public void close() throws NamingException
	{
		delegate.close();
	}

	/**
	 * @return
	 * @throws NamingException
	 * @see javax.naming.Context#getNameInNamespace()
	 */
	public String getNameInNamespace() throws NamingException
	{
		return delegate.getNameInNamespace();
	}
	

}

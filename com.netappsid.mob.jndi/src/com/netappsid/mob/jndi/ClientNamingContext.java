/**
 * 
 */
package com.netappsid.mob.jndi;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.hazelcast.client.HazelcastClient;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class ClientNamingContext implements Context
{
	private Map<String, String> distributedJNDI;
	
	/**
	 * 
	 */
	public ClientNamingContext()
	{
		HazelcastClient newHazelcastClient = HazelcastClient.newHazelcastClient("naid", "naid", "localhost");
		distributedJNDI = newHazelcastClient.getMap("hajndi");
	}
	
	/* (non-Javadoc)
	 * @see javax.naming.Context#lookup(javax.naming.Name)
	 */
	@Override
	public Object lookup(Name name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#lookup(java.lang.String)
	 */
	@Override
	public Object lookup(String name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#bind(javax.naming.Name, java.lang.Object)
	 */
	@Override
	public void bind(Name name, Object obj) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#bind(java.lang.String, java.lang.Object)
	 */
	@Override
	public void bind(String name, Object obj) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#rebind(javax.naming.Name, java.lang.Object)
	 */
	@Override
	public void rebind(Name name, Object obj) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#rebind(java.lang.String, java.lang.Object)
	 */
	@Override
	public void rebind(String name, Object obj) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#unbind(javax.naming.Name)
	 */
	@Override
	public void unbind(Name name) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#unbind(java.lang.String)
	 */
	@Override
	public void unbind(String name) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#rename(javax.naming.Name, javax.naming.Name)
	 */
	@Override
	public void rename(Name oldName, Name newName) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#rename(java.lang.String, java.lang.String)
	 */
	@Override
	public void rename(String oldName, String newName) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#list(javax.naming.Name)
	 */
	@Override
	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#list(java.lang.String)
	 */
	@Override
	public NamingEnumeration<NameClassPair> list(String name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#listBindings(javax.naming.Name)
	 */
	@Override
	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#listBindings(java.lang.String)
	 */
	@Override
	public NamingEnumeration<Binding> listBindings(String name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#destroySubcontext(javax.naming.Name)
	 */
	@Override
	public void destroySubcontext(Name name) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#destroySubcontext(java.lang.String)
	 */
	@Override
	public void destroySubcontext(String name) throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#createSubcontext(javax.naming.Name)
	 */
	@Override
	public Context createSubcontext(Name name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#createSubcontext(java.lang.String)
	 */
	@Override
	public Context createSubcontext(String name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#lookupLink(javax.naming.Name)
	 */
	@Override
	public Object lookupLink(Name name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#lookupLink(java.lang.String)
	 */
	@Override
	public Object lookupLink(String name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#getNameParser(javax.naming.Name)
	 */
	@Override
	public NameParser getNameParser(Name name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#getNameParser(java.lang.String)
	 */
	@Override
	public NameParser getNameParser(String name) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#composeName(javax.naming.Name, javax.naming.Name)
	 */
	@Override
	public Name composeName(Name name, Name prefix) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#composeName(java.lang.String, java.lang.String)
	 */
	@Override
	public String composeName(String name, String prefix) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#addToEnvironment(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object addToEnvironment(String propName, Object propVal) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#removeFromEnvironment(java.lang.String)
	 */
	@Override
	public Object removeFromEnvironment(String propName) throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#getEnvironment()
	 */
	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.naming.Context#close()
	 */
	@Override
	public void close() throws NamingException
	{}

	/* (non-Javadoc)
	 * @see javax.naming.Context#getNameInNamespace()
	 */
	@Override
	public String getNameInNamespace() throws NamingException
	{
		return null;
	}

}

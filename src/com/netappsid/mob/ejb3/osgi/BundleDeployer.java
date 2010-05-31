package com.netappsid.mob.ejb3.osgi;

import java.util.List;

import org.osgi.framework.Bundle;

/**
 * this class is use to wrap information have to be send to EJB3 deployer
 * 
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 */
public class BundleDeployer
{
	private final Bundle bundle;
	private final String packageRestriction;
	private List<Class<?>> ejb3ClassList;

	public BundleDeployer(Bundle bundle, String packageRestriction)
	{
		this(bundle, packageRestriction, null);
	}

	public BundleDeployer(Bundle bundle, String packageRestriction, List<Class<?>> ejb3ClassList)
	{
		this.bundle = bundle;
		this.packageRestriction = packageRestriction;
		this.ejb3ClassList = ejb3ClassList;
	}

	public Bundle getBundle()
	{
		return bundle;
	}

	public String getPackageRestriction()
	{
		return packageRestriction;
	}

	public void setEjb3ClassList(List<Class<?>> ejb3ClassList)
	{
		this.ejb3ClassList = ejb3ClassList;
	}

	public List<Class<?>> getEjb3ClassList()
	{
		return ejb3ClassList;
	}
}

/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
@Stateless
public class TestInjectServiceBean
{
	@EJB
	private TestServiceBean testServiceBean;

	@Resource
	private SessionContext context;

	public boolean isInjectEJB()
	{
		return testServiceBean != null;
	}

	public boolean isInjectSessionContext()
	{
		return context != null;
	}
}

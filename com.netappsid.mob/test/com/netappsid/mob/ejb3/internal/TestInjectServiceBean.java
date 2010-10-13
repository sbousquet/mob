/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import javax.ejb.EJB;
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

	public boolean isInject()
	{
		return testServiceBean != null;
	}
}

/**
 * 
 */
package com.netappsid.mod.ejb3.internal;

import javax.ejb.Stateless;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
@Stateless
public class TestServiceBean
{
	public void testMethodThrowException()
	{
		throw new RuntimeException();
	}
}

package com.netappsid.mob.ejb3.test.beans;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.netappsid.mob.ejb3.test.IdentityTest;

@Stateless
public class TestServiceBean implements TestServiceRemote
{
	
	private String postConstruct;
	
	@PostConstruct
	public void postConstruct()
	{
		postConstruct = "Test must set";
	}
	
	/**
	 * @return the postConstruct
	 */
	@Override
	public String getPostConstruct()
	{
		return postConstruct;
	}
	
	@Override
	public IdentityTest testIdentity(IdentityTest identityTest)
	{
		identityTest.setIdentity(System.identityHashCode(this));
		return identityTest;
	}
	
	@Override
	public void testMethod()
	{
		System.out.println("method");
	}

	/* (non-Javadoc)
	 * @see com.netappsid.mod.ejb3.test.beans.TestServiceRemote#testSleep()
	 */
	@Override
	public void testSleep()
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
}

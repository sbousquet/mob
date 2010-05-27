package com.netappsid.mod.ejb3.test.beans;

import javax.ejb.Stateless;

import com.netappsid.mod.ejb3.test.IdentityTest;

@Stateless
public class TestServiceBean implements TestServiceRemote
{
	
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
	
}

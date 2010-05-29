package com.netappsid.mod.ejb3.test.beans;

import javax.ejb.Remote;

import com.netappsid.mod.ejb3.test.IdentityTest;

@Remote
public interface TestServiceRemote
{

	void testSleep();
	
	IdentityTest testIdentity(IdentityTest identityTest);

	void testMethod();

}

package com.netappsid.mob.ejb3.test.beans;

import javax.ejb.Remote;

import com.netappsid.mob.ejb3.test.IdentityTest;

@Remote
public interface TestServiceRemote
{

	void testSleep();
	
	IdentityTest testIdentity(IdentityTest identityTest);

	void testMethod();

	/**
	 * @return
	 */
	String getPostConstruct();

}

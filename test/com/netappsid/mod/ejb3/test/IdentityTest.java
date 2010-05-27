package com.netappsid.mod.ejb3.test;

import java.io.Serializable;

public class IdentityTest implements Serializable
{
	private int identity;

	public void setIdentity(int identity)
	{
		this.identity = identity;
	}

	public int getIdentity()
	{
		return identity;
	}
}

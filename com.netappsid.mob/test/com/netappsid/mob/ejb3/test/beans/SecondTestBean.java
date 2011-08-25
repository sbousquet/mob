package com.netappsid.mob.ejb3.test.beans;

import javax.ejb.Stateless;

@Stateless
public class SecondTestBean implements SecondTestLocal
{
	@Override
	public void test()
	{
		System.out.println("hehe");
	}
}

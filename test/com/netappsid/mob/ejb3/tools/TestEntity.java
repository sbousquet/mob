/**
 * 
 */
package com.netappsid.mob.ejb3.tools;

import java.util.List;

import javax.persistence.Entity;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
@Entity
public class TestEntity
{
	private TestEntity2 test2;
	private List<TestEntity3> test3;

	public void setTest2(TestEntity2 test2)
	{
		this.test2 = test2;
	}

	public TestEntity2 getTest2()
	{
		return test2;
	}

	public void setTest3(List<TestEntity3> test3)
	{
		this.test3 = test3;
	}

	public List<TestEntity3> getTest3()
	{
		return test3;
	} 
}

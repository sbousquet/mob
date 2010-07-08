/**
 * 
 */
package com.netappsid.mob.ejb3.tools;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.Set;

import org.junit.Test;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class JPAHelperTest
{

	/**
	 * Test method for {@link com.netappsid.mob.ejb3.tools.JPAHelper#buildEntityGraph(java.lang.Class)}.
	 * @throws IntrospectionException 
	 */
	@Test
	public void testBuildEntityGraph() throws IntrospectionException
	{
		Set<Class<?>> buildEntityGraph = JPAHelper.buildEntityGraph(TestEntity.class);
		assertTrue(buildEntityGraph.size()==3);
		assertTrue(buildEntityGraph.contains(TestEntity.class));
		assertTrue(buildEntityGraph.contains(TestEntity2.class));
		assertTrue(buildEntityGraph.contains(TestEntity3.class));
	}

}

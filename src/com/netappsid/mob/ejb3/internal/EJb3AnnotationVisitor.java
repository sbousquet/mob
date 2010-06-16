/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class EJb3AnnotationVisitor extends EmptyVisitor
{

	private static Set<String> ejb3Type = new HashSet<String>(Arrays.asList("Ljavax/ejb/Stateless;", "Ljavax/ejb/Statefull;", "Ljavax/persistence/Entity;"));

	private boolean isEjbClass = false;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.objectweb.asm.commons.EmptyVisitor#visitAnnotation(java.lang.String, boolean)
	 */
	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible)
	{
		
		if(!isEjbClass)
		{
			isEjbClass = ejb3Type.contains(desc);
		}
		
		return super.visitAnnotation(desc, visible);
	}


	public boolean isEjbClass()
	{
		return isEjbClass;
	}
}

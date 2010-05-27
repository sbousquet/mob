/**
 * 
 */
package com.netappsid.mod.ejb3.io;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public interface IMultiVersionClassResolver
{
	Class<?> resolveClass(String className, String versionString,String splitDelimiter) throws ClassNotFoundException;
}

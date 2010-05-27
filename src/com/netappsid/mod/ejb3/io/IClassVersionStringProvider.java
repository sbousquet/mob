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
public interface IClassVersionStringProvider
{
	String getClassVersionString(Class<?> clazz,String splitDelimiter); 
}

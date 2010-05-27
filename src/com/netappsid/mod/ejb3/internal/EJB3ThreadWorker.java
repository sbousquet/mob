/**
 * 
 */
package com.netappsid.mod.ejb3.internal;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class EJB3ThreadWorker extends Thread
{
	/**
	 * 
	 */
	public EJB3ThreadWorker(Runnable runnable)
	{
		super(runnable,"EJB3 Worker Thread");
	}
}

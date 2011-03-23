/**
 * 
 */
package com.netappsid.mob.ejb3.internal;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

import javassist.Modifier;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import javax.naming.RefAddr;
import javax.naming.Referenceable;

import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netappsid.mob.ejb3.EJB3ServiceHandler;
import com.netappsid.mob.ejb3.EJBServiceLink;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.13 $
 */
public abstract class AbstractService extends RefAddr implements EJb3Service, Referenceable
{
	private static Logger logger = LoggerFactory.getLogger(AbstractService.class);

	private EJBServiceLink ejbLink;

	private ExecutorService executor;

	private final EJB3BundleUnit bundleUnit;

	public AbstractService(ExecutorService executorService,EJBServiceLink ejbServiceLink,EJB3BundleUnit bundleUnit)
	{
		super(EJB3SERVICE);
		executor = executorService;
		ejbLink = ejbServiceLink;
		this.bundleUnit = bundleUnit;
	}

	protected Class<?> modifyClass(Class<?> original) throws Exception
	{

		ProxyFactory f = new ProxyFactory();

		f.setSuperclass(original);
		f.setInterfaces(new Class[] { EJB3ServiceHandler.class });

		f.setFilter(new MethodFilter()
			{
				public boolean isHandled(Method method)
				{
					return (!Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers()));
				}
			});
		Class c = f.createClass();

		return c;
	}

	protected abstract MethodHandler getMethodHandler(Class<?> originalClass) throws InstantiationException, IllegalAccessException;
	

	public void setEjbLink(EJBServiceLink ejbLink)
	{
		this.ejbLink = ejbLink;
	}

	public EJBServiceLink getEjbLink()
	{
		return ejbLink;
	}

	public void setExecutor(ExecutorService executor)
	{
		this.executor = executor;
	}

	public ExecutorService getExecutor()
	{
		return executor;
	}

	public EJB3BundleUnit getBundleUnit()
	{
		return bundleUnit;
	}

}

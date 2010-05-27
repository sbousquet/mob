/**
 * 
 */
package com.netappsid.mod.ejb3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class RemoteServicesRegistry
{
	private Map<String, Map<String, Class>> servicesByContext = new HashMap<String, Map<String, Class>>();

	public void registerService(String context, String beanName, Class service)
	{
		if (servicesByContext.containsKey(context))
		{
			servicesByContext.get(context).put(beanName, service);
		}
		else
		{
			Map<String, Class> services = new HashMap<String, Class>();
			services.put(beanName, service);

			servicesByContext.put(context, services);
		}
	}

	public void unregisterContext(String context)
	{
		servicesByContext.remove(context);
	}

	public void unregisterService(String context, Class service)
	{
		servicesByContext.get(context).remove(service);
	}

	public Map<String, Map<String, Class>> getRemoteServices()
	{
		return servicesByContext;
	}
}

package com.netappsid.mob.ejb3.jndi;

import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netappsid.mob.ejb3.internal.EJB3BundleUnit;


/**
 * 
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.4 $
 */
public class JNDIEntityManager implements Referenceable
{
	private static Logger logger = LoggerFactory.getLogger(JNDIEntityManager.class);
	private final EJB3BundleUnit bundleUnit;

	public JNDIEntityManager(EJB3BundleUnit bundleUnit)
	{
		this.bundleUnit = bundleUnit;
	}

	public Object getValue()
	{
		EntityManager manager = bundleUnit.getManager();
		return manager;
	}
	
	/**
     * @see Referenceable
     */
    public Reference getReference () throws NamingException
    {
        RefAddr ra = new StringRefAddr ( "EntityManager", bundleUnit.getName() );
        
        Reference ref = new Reference ( getClass ().getName (),
                new StringRefAddr ( "name", "JNDIEntityManager" ),
                JNDIEntityManagerFactory.class.getName (), JNDIEntityManagerFactory.class.getResource("/").toString() );
        
        ref.add ( ra );
        return ref;

    }

}

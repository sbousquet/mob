/**
 * 
 */
package com.netappsid.mob.ejb3.security;

import java.security.Principal;

/** An implementation of Principal and Comparable that represents no role.
Any Principal or name of a Principal when compared to an NobodyPrincipal
using {@link #equals(Object) equals} or {@link #compareTo(Object) compareTo} 
will always be found not equal to the NobodyPrincipal.

Note that this class is not likely to operate correctly in a collection
since the hashCode() and equals() methods are not correlated.

@author Scott.Stark@jboss.org
@version $Revision: 1.2 $
*/
public class NobodyPrincipal implements Comparable, Principal
{
    public static final String NOBODY = "<NOBODY>";
    public static final NobodyPrincipal NOBODY_PRINCIPAL = new NobodyPrincipal();

    @Override
    public int hashCode()
    {
        return NOBODY.hashCode();
    }

    /**
    @return "<NOBODY>"
    */
    public String getName()
    {
        return NOBODY;
    }

    @Override
    public String toString()
    {
        return NOBODY;
    }
    
    /** This method always returns 0 to indicate equality for any argument.
    This is only meaningful when comparing against other Principal objects
     or names of Principals.

    @return false to indicate inequality for any argument.
    */
    @Override
    public boolean equals(Object another)
    {
        return false;
    }

    /** This method always returns 1 to indicate inequality for any argument.
    This is only meaningful when comparing against other Principal objects
     or names of Principals.

    @return 1 to indicate inequality for any argument.
    */
    public int compareTo(Object o)
    {
        return 1;
    }

}


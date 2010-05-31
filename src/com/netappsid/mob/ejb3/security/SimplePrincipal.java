/**
 * 
 */
package com.netappsid.mob.ejb3.security;

import java.security.Principal;

/**
 * A simple String based implementation of Principal. Typically a
 * SimplePrincipal is created given a userID which is used as the Principal
 * name.
 * @author <a href="on@ibis.odessa.ua">Oleg Nitz</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.2 $
 */
public class SimplePrincipal implements Principal, java.io.Serializable
{
   static final long serialVersionUID = 7701951188631723261L;
   private String name;

   public SimplePrincipal(String name)
   {
      this.name = name;
   }

   /**
    * Compare this SimplePrincipal's name against another Principal
    * @return true if name equals another.getName();
    */
   @Override
   public boolean equals(Object another)
   {
      if (!(another instanceof Principal))
         return false;
      String anotherName = ((Principal) another).getName();
      boolean equals = false;
      if (name == null)
         equals = anotherName == null;
      else
         equals = name.equals(anotherName);
      return equals;
   }

   @Override
   public int hashCode()
   {
      return (name == null ? 0 : name.hashCode());
   }

   @Override
   public String toString()
   {
      return name;
   }

   public String getName()
   {
      return name;
   }
} 

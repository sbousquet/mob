/**
 * 
 */
package com.netappsid.mod.ejb3.security;

/**
 * The CallerIdentity is a principal that may have a credential.
 *
 * @author Thomas.Diesler@jboss.org
 * @version $Revision: 1.2 $
 */
public class CallerIdentity extends SimplePrincipal
{
   /** The run-as role */
   private Object credential;

   // hash code cache
   private int hashCode;

   /**
    * Construct an unmutable instance of a CallerIdentity
    */
   public CallerIdentity(String principal, Object credential)
   {
      super(principal);
      this.credential = credential;
   }

   public Object getCredential()
   {
      return credential;
   }

   /**
    * Returns a string representation of the object.
    * @return a string representation of the object.
    */
   @Override
   public String toString()
   {
      return "[principal=" + getName() + "]";
   }

   /**
    * Indicates whether some other object is "equal to" this one.
    */
   @Override
   public boolean equals(Object obj)
   {
      if (obj == null) return false;
      if (obj instanceof CallerIdentity)
      {
         CallerIdentity other = (CallerIdentity)obj;
         return getName().equals(other.getName());
      }
      return false;
   }

   /**
    * Returns a hash code value for the object.
    */
   @Override
   public int hashCode()
   {
      if (hashCode == 0)
      {
         hashCode = toString().hashCode();
      }
      return hashCode;
   }
}

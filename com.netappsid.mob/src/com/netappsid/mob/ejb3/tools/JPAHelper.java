/**
 * 
 */
package com.netappsid.mob.ejb3.tools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class JPAHelper
{
	public static Set<Class<?>> buildEntityGraph(Class<?> toInspect) throws IntrospectionException
	{
		Set<Class<?>> entityClass = new HashSet<Class<?>>();

		if (toInspect.isAnnotationPresent(Entity.class))
		{
			entityClass.add(toInspect);
		}
		else
		{
			throw new IllegalArgumentException("Your root class must be an entity class");
		}

		recurseSuperClass(toInspect.getSuperclass(), entityClass);

		visitProperties(toInspect, entityClass);

		return entityClass;
	}

	/**
	 * @param toInspect
	 * @param entitiesClass
	 * @throws IntrospectionException
	 */
	private static void visitProperties(Class<?> toInspect, Set<Class<?>> entitiesClass) throws IntrospectionException
	{
		BeanInfo beanInfo = Introspector.getBeanInfo(toInspect);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		for (PropertyDescriptor propertyDescriptor : propertyDescriptors)
		{
			Method readMethod = propertyDescriptor.getReadMethod();

			if(readMethod == null)
			{
				continue;
			}
			
			if (readMethod.isAnnotationPresent(OneToMany.class) || readMethod.isAnnotationPresent(OneToOne.class)
					|| readMethod.isAnnotationPresent(ManyToMany.class) || readMethod.isAnnotationPresent(ManyToOne.class))
			{

				Class<?> propertyType = propertyDescriptor.getPropertyType();

				if (propertyType.isAnnotationPresent(Entity.class) && !entitiesClass.contains(propertyType))
				{
					entitiesClass.add(propertyType);
					recurseSuperClass(propertyType, entitiesClass);
					visitProperties(propertyType, entitiesClass);
				}
				else if (Collection.class.isAssignableFrom(propertyType))
				{

					Class<?> entity = (Class<?>) ((ParameterizedType) propertyDescriptor.getReadMethod().getGenericReturnType()).getActualTypeArguments()[0];
					if (entity.isAnnotationPresent(Entity.class) && !entitiesClass.contains(entity))
					{
						entitiesClass.add(entity);
						recurseSuperClass(entity, entitiesClass);
						visitProperties(entity, entitiesClass);
					}
				}
				else if (Map.class.isAssignableFrom(propertyType))
				{

					Class<?> entity = (Class<?>) ((ParameterizedType) propertyDescriptor.getReadMethod().getGenericReturnType()).getActualTypeArguments()[1];
					if (entity.isAnnotationPresent(Entity.class) && !entitiesClass.contains(entity))
					{
						entitiesClass.add(entity);
						recurseSuperClass(entity, entitiesClass);
						visitProperties(entity, entitiesClass);
					}
				}
			}
		}

	}

	/**
	 * @param superclass
	 * @param entityClass
	 */
	private static void recurseSuperClass(Class<?> superclass, Set<Class<?>> entityClass)
	{
		if (superclass.isAnnotationPresent(Entity.class))
		{
			entityClass.add(superclass);
		}

		if (superclass.getSuperclass() != null)
		{
			recurseSuperClass(superclass.getSuperclass(), entityClass);
		}
	}
}

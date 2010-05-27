package com.netappsid.mod.ejb3.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version $Revision: 1.1 $
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EntityManager {
	String value();
}

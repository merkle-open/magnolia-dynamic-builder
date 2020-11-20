package com.namics.oss.magnolia.appbuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define an AppLauncherGroup
 * <p>
 * If the a group with the specified name is already present
 * it will be ignored.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AppLauncherGroup {
	String name();
}

package com.namics.oss.magnolia.appbuilder.annotations;

import com.namics.oss.magnolia.appbuilder.launcher.group.SimpleGroupDefinition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark the method which
 * returns a {@link SimpleGroupDefinition}
 * in an {@link AppLauncherGroup} annotated class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GroupDefinition {
}

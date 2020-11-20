package com.namics.oss.magnolia.appbuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods which produce
 * an {@link info.magnolia.cms.security.operations.AccessDefinition}
 * for a AppLauncherGroup.
 * <p>
 * - Used in classes annotated with {@link AppFactory}, optional.
 * Annotation is only considered
 * - if an {@link AppLauncherGroup} annotation is present
 * - if the group specified in the {@link AppLauncherGroup} annotation is new
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LauncherGroupPermissions {
}

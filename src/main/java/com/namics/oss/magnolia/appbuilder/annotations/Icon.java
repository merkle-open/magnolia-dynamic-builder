package com.namics.oss.magnolia.appbuilder.annotations;

import info.magnolia.icons.MagnoliaIcons;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to provide the icon for an App.
 * <p>
 * - Used in classes annotated with {@link AppFactory}, optional.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Icon {
	MagnoliaIcons value();
}

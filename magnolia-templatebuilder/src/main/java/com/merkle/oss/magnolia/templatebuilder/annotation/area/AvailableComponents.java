package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to set the available components in an area by their id. When used in combination with
 * {@link AvailableComponentClasses} components from both annotations are used.
 *
 * @see Area
 * @see AvailableComponentClasses
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AvailableComponents {

    String[] value() default {};
}

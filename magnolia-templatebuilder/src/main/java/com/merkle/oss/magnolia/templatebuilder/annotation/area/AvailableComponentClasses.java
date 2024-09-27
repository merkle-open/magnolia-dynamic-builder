package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to set the available components in an area either by specifying the component class directly or by
 * specifying a component category annotation, in which case all components have that category annotation will be
 * available. When used in combination with {@link AvailableComponents} components from both annotations are used.
 *
 * @see Area
 * @see AvailableComponents
 * @see ComponentCategory
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AvailableComponentClasses {
    Class<?>[] value() default {};
}

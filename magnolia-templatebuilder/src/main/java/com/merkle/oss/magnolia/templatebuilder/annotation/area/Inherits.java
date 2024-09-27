package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to configure how an area inherits from parent pages.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Inherits {
    ComponentInheritanceMode components() default ComponentInheritanceMode.FILTERED;
    PropertyInheritanceMode properties() default PropertyInheritanceMode.NONE;
}

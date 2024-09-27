package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merkle.oss.magnolia.templatebuilder.annotation.TernaryBoolean;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Area {

    String id();

    String name();

    /**
     * Template renderer will be set if not specified
     */
    String renderer() default "";

    String templateScript() default "";

    String title() default "";

    String dialog() default "";

    AreaType type() default AreaType.LIST;

    TernaryBoolean optional() default TernaryBoolean.UNSPECIFIED;

    int maxComponents() default Integer.MAX_VALUE;

    TernaryBoolean createAreaNode() default TernaryBoolean.UNSPECIFIED;
}

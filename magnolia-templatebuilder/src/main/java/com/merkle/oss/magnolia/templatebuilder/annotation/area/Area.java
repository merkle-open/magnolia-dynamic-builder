package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merkle.oss.magnolia.builder.annotation.TernaryBoolean;
import com.merkle.oss.magnolia.builder.annotation.Unspecified;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Area {

    String id();

    String name();

    /**
     * Template renderer will be set if not specified
     */
    String renderer() default Unspecified.STRING;

    String templateScript() default Unspecified.STRING;

    String title() default Unspecified.STRING;

    String dialog() default Unspecified.STRING;

    AreaType type() default AreaType.LIST;

    TernaryBoolean optional() default TernaryBoolean.UNSPECIFIED;

    int maxComponents() default Unspecified.INT;

    TernaryBoolean createAreaNode() default TernaryBoolean.UNSPECIFIED;
}

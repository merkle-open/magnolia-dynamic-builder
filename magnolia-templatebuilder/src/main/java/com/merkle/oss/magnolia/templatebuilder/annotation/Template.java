package com.merkle.oss.magnolia.templatebuilder.annotation;

import info.magnolia.rendering.template.type.DefaultTemplateTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Template {

    String id();

    String renderer();

    Class<?> modelClass() default Class.class;

    String title() default "";

    String description() default "";

    String dialog() default "";

    String templateScript() default "";

    /**
     * Defines the visibility of the template. When set to false the template is never presented in the user interface.
     * This is useful for templates that are only used for pages that are created by scheduled jobs rather than by
     * editors.
     */
    TernaryBoolean visible() default TernaryBoolean.UNSPECIFIED;

    /**
     * Specifies the template type, e.g. "home", "section", etc.
     *
     * @see info.magnolia.rendering.template.type.DefaultTemplateTypes
     */
    String type() default DefaultTemplateTypes.CONTENT;

    /**
     * Specifies the template subtype, such as specific features, e.g. "news", etc.
     */
    String subtype() default "";

    /**
     * Specifies whether a component can be changed.
     */
    TernaryBoolean writable() default TernaryBoolean.UNSPECIFIED;

    /**
     * Specifies whether a component can be moved.
     */
    TernaryBoolean moveable() default TernaryBoolean.UNSPECIFIED;

    /**
     * Specifies whether a component can be deleted.
     */
    TernaryBoolean deletable() default TernaryBoolean.UNSPECIFIED;
}

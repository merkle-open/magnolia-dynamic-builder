package com.merkle.oss.magnolia.templatebuilder.annotation;

import info.magnolia.rendering.template.type.DefaultTemplateTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merkle.oss.magnolia.templatebuilder.definition.PermissionPredicate;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Template {

    String id();

    String renderer();

    Class<?> modelClass() default Class.class;

    String title() default "";

    String description() default "";

    String dialog() default "";

    String templateScript() default "undefined";

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
     * fallback permission - applies to all other permissions if not explicitly specified
     */
    Permission permission() default @Permission(predicate = PermissionPredicate.Allowed.class);

    /**
     * Specifies whether the template is available to editors.
     */
    Permission visible() default @Permission();

    /**
     * Specifies whether a component can be changed.
     */
    Permission writable() default @Permission();

    /**
     * Specifies whether a component can be changed.
     */
    Permission editable() default @Permission();

    /**
     * Specifies whether a component can be moved.
     */
     Permission moveable() default @Permission();

    /**
     * Specifies whether a component can be deleted.
     */
     Permission deletable() default @Permission();

     @interface Permission {
         Param[] params() default {};
         Class<? extends PermissionPredicate> predicate() default PermissionPredicate.Unspecified.class;

         @interface Param {
             String key();
             String value();
         }
     }
}

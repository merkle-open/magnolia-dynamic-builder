package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes an annotation as a component category annotation.
 *
 * A component category annotation is used to annotate components as being part of a certain category. The annotation
 * is then specified in the `@AvailableComponentClasses` annotation on areas. All components in the category will
 * then be available in the area.
 * <p/>
 * <pre>
 * &#64;Retention(RetentionPolicy.RUNTIME)
 * &#64;Target(ElementType.TYPE)
 * &#64;ComponentCategory
 * public &#64;interface Promo {
 * }
 * </pre>
 *
 * @see com.merkle.oss.magnolia.templatebuilder.annotation.Template
 * @see Area
 * @see AvailableComponentClasses
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ComponentCategory {
}

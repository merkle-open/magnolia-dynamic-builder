package com.merkle.oss.magnolia.templatebuilder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a component/area as dynamicFragment
 * <br>
 * Can be used to disable caching of a component or an area
 * <br>
 * <br>See <a href="https://docs.magnolia-cms.com/product-docs/6.2/Modules/List-of-modules/High-performance-caching-modules/Advanced-Cache-Dynamic-Page-Caching.html#_dynamic_page_caching">Dynamic page caching</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface DynamicFragment {
    long ttlInSeconds() default 0;
}

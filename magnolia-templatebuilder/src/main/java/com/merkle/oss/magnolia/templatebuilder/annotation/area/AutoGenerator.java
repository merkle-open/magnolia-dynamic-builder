package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method to be called when an area is rendered to perform automatic generation of content. Often used for
 * adding components automatically when a new page is created so editors don't have to do it manually every time.
 * @see Area
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoGenerator {}
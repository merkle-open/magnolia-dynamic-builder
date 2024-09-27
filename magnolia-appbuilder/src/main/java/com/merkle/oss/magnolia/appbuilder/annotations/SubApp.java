package com.merkle.oss.magnolia.appbuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods which produce
 * a {@link info.magnolia.ui.api.app.SubAppDescriptor}.
 * <p>
 * An AppFactory annotated class should have at least
 * one SubApp.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SubApp {
}

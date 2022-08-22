package com.namics.oss.magnolia.appbuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AppFactory {

	String id();

	String name();

	String label();

	String icon();

	String i18nBasename() default "";

	String theme() default "";

	boolean isEnabled() default true;

	/**
	 * Leave empty to use admincentral/config.yaml
	 */
	String launcherGroup() default "";

	/**
	 * Leave empty to use admincentral/config.yaml
	 */
	int order() default Integer.MAX_VALUE;
}

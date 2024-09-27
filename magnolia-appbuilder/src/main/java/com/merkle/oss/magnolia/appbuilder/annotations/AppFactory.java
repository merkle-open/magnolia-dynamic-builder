package com.merkle.oss.magnolia.appbuilder.annotations;

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

	/**
	 * @deprecated use {@link Icon} or {@link CustomIcon} annotation
	 */
	@Deprecated
	String icon() default "";

	String i18nBasename() default "";

	String theme() default "";

	boolean isEnabled() default true;

	boolean generateLegacyChooserApp() default false;
}

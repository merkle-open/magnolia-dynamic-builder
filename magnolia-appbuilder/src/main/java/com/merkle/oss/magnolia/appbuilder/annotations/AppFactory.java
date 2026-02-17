package com.merkle.oss.magnolia.appbuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merkle.oss.magnolia.builder.annotation.Unspecified;

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
	String icon() default Unspecified.STRING;

	String i18nBasename() default Unspecified.STRING;

	String theme() default Unspecified.STRING;

	boolean isEnabled() default true;

	boolean generateLegacyChooserApp() default false;
}

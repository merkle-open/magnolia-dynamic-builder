package com.merkle.oss.magnolia.templatebuilder.configuration;

import info.magnolia.objectfactory.guice.AbstractGuiceComponentConfigurer;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.merkle.oss.magnolia.templatebuilder.annotation.TemplateFactories;

public class TemplateBuilderGuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
	@Override
	protected void configure() {
		super.configure();
		Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>(){}, TemplateFactories.class);
	}
}

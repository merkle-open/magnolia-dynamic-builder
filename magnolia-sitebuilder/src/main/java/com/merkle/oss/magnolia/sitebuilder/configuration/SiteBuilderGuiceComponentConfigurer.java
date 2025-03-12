package com.merkle.oss.magnolia.sitebuilder.configuration;

import info.magnolia.objectfactory.guice.AbstractGuiceComponentConfigurer;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.merkle.oss.magnolia.sitebuilder.annotation.SiteFactories;

public class SiteBuilderGuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
	@Override
	protected void configure() {
		super.configure();
		Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>(){}, SiteFactories.class);
	}
}

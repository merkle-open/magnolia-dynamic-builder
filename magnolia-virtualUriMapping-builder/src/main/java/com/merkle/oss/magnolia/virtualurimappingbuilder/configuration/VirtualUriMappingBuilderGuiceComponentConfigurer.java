package com.merkle.oss.magnolia.virtualurimappingbuilder.configuration;

import info.magnolia.objectfactory.guice.AbstractGuiceComponentConfigurer;
import info.magnolia.virtualuri.VirtualUriMapping;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.merkle.oss.magnolia.virtualurimappingbuilder.annotation.VirtualUriMappers;

public class VirtualUriMappingBuilderGuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
	@Override
	protected void configure() {
		super.configure();
		Multibinder.newSetBinder(binder(), new TypeLiteral<Class<? extends VirtualUriMapping>>(){}, VirtualUriMappers.class);
	}
}

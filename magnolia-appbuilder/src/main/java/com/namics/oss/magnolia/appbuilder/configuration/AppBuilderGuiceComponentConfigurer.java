package com.namics.oss.magnolia.appbuilder.configuration;

import info.magnolia.objectfactory.guice.AbstractGuiceComponentConfigurer;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.namics.oss.magnolia.appbuilder.AppRegistrar;
import com.namics.oss.magnolia.appbuilder.ChooserDialogRegistrar;

public class AppBuilderGuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
	@Override
	protected void configure() {
		super.configure();
		Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>(){}, AppRegistrar.AppFactories.class);
		Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>(){}, ChooserDialogRegistrar.ChooserDialogFactories.class);
	}
}

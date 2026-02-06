package com.merkle.oss.magnolia.appbuilder.configuration;

import com.merkle.oss.magnolia.appbuilder.annotations.SearchResultSuppliers;
import info.magnolia.objectfactory.guice.AbstractGuiceComponentConfigurer;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.merkle.oss.magnolia.appbuilder.annotations.AppFactories;
import com.merkle.oss.magnolia.appbuilder.annotations.ChooserDialogFactories;

public class AppBuilderGuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
	@Override
	protected void configure() {
		super.configure();
		Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>(){}, AppFactories.class);
		Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>(){}, ChooserDialogFactories.class);
		Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>(){}, SearchResultSuppliers.class);
	}
}

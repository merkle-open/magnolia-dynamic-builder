package com.namics.oss.magnolia.appbuilder;

import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.app.registry.AppDescriptorRegistry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandles;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.BindingAnnotation;

public class AppRegistrar {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final Set<Class<?>> appFactories;
	private final AppDescriptorRegistry appDescriptorRegistry;

	@Inject
	public AppRegistrar(
			@AppFactories final Set<Class<?>> appFactories,
			final AppDescriptorRegistry appDescriptorRegistry
	) {
		this.appFactories = appFactories;
		this.appDescriptorRegistry = appDescriptorRegistry;
	}

	public void register() {
		appFactories.forEach(this::register);
	}

	private void register(final Class<?> factoryClass) {
		LOG.info("Detected app bean with name '{}'", factoryClass.getName());
		// build app descriptor from detected factory bean
		final Object factory = Components.newInstance(factoryClass);
		final AppDescriptorProvider appDescriptorProvider = new AppDescriptorProvider(factory);
		final LegacyAppDescriptorProvider legacyAppDescriptorProvider = new LegacyAppDescriptorProvider(factory);
		// register app descriptor
		if(legacyAppDescriptorProvider.shouldRegister()) {
			LOG.info("Registered legacy chooser app '{}'", legacyAppDescriptorProvider.getMetadata().getReferenceId());
			appDescriptorRegistry.register(legacyAppDescriptorProvider);
		}
		appDescriptorRegistry.register(appDescriptorProvider);
		LOG.info("Registered app '{}'", appDescriptorProvider.getMetadata().getReferenceId());
	}

	@BindingAnnotation
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.PARAMETER})
	public @interface AppFactories {
	}
}

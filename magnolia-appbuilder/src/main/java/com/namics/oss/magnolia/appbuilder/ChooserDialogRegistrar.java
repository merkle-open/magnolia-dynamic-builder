package com.namics.oss.magnolia.appbuilder;

import info.magnolia.objectfactory.Components;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;

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

public class ChooserDialogRegistrar {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final Set<Class<?>> chooserDialogFactories;
	private final DialogDefinitionRegistry dialogDefinitionRegistry;

	@Inject
	public ChooserDialogRegistrar(
			@ChooserDialogFactories final Set<Class<?>> chooserDialogFactories,
			final DialogDefinitionRegistry dialogDefinitionRegistry
	) {
		this.chooserDialogFactories = chooserDialogFactories;
		this.dialogDefinitionRegistry = dialogDefinitionRegistry;
	}

	public void register() {
		chooserDialogFactories.forEach(this::register);
	}

	private void register(final Class<?> factoryClass) {
		LOG.info("Detected chooser dialog factory bean with name '{}'", factoryClass.getName());
		final Object factory = Components.newInstance(factoryClass);
		final ChooserDialogDefinitionProvider<?> chooserDialogDefinitionProvider = new ChooserDialogDefinitionProvider<>(factory);
		dialogDefinitionRegistry.register(chooserDialogDefinitionProvider);
		LOG.info("Registered chooser dialog '{}'", chooserDialogDefinitionProvider.getMetadata().getReferenceId());
	}

	@BindingAnnotation
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.PARAMETER})
	public @interface ChooserDialogFactories {
	}
}

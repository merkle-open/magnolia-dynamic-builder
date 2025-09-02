package com.merkle.oss.magnolia.appbuilder;

import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.ui.dialog.DefinitionTypes;
import info.magnolia.ui.dialog.DialogDefinition;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merkle.oss.magnolia.appbuilder.annotations.ChooserDialogFactories;
import com.merkle.oss.magnolia.builder.AbstractDynamicConfigurationSource;

import jakarta.inject.Inject;

public class ChooserDialogConfigurationSource extends AbstractDynamicConfigurationSource<DialogDefinition> {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ChooserDialogDefinitionProvider.Factory chooserDialogDefinitionProviderFactory;

    @Inject
	public ChooserDialogConfigurationSource(
			@ChooserDialogFactories final Set<Class<?>> chooserDialogFactories,
			final ChooserDialogDefinitionProvider.Factory chooserDialogDefinitionProviderFactory
	) {
		super(DefinitionTypes.DIALOG, chooserDialogFactories);
        this.chooserDialogDefinitionProviderFactory = chooserDialogDefinitionProviderFactory;
    }

	@Override
	protected Stream<DefinitionProvider<DialogDefinition>> definitionProviders(final Class<?> factoryClass) {
		LOG.info("Registered dialog '{}' from {}", factoryClass.getSimpleName(), factoryClass.getName());
		return Stream.of(chooserDialogDefinitionProviderFactory.create(factoryClass));
	}
}

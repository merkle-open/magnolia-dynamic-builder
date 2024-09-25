package com.namics.oss.magnolia.appbuilder;

import static com.google.common.base.CaseFormat.*;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.config.registry.DefinitionType;
import info.magnolia.config.registry.RegistryMap;
import info.magnolia.config.source.ConfigurationSource;
import info.magnolia.config.source.ConfigurationSourceType;
import info.magnolia.config.source.ConfigurationSourceTypes;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.dialog.DefinitionTypes;
import info.magnolia.ui.dialog.DialogDefinition;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.namics.oss.magnolia.appbuilder.annotations.ChooserDialogFactories;

public class ChooserDialogConfigurationSource implements ConfigurationSource<DialogDefinition> {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final RegistryMap<DialogDefinition> registryMap = new RegistryMap<>();
	private final Set<Class<?>> chooserDialogFactories;

	@Inject
	public ChooserDialogConfigurationSource(@ChooserDialogFactories final Set<Class<?>> chooserDialogFactories) {
		this.chooserDialogFactories = chooserDialogFactories;
	}

	@Override
	public ConfigurationSourceType type() {
		return ConfigurationSourceTypes.code;
	}

	@Override
	public DefinitionType getDefinitionType() {
		return DefinitionTypes.DIALOG;
	}

	@Override
	public void start() {
		LOG.info("Setting up {} for {} definitions", getClass().getSimpleName(), LOWER_CAMEL.to(LOWER_HYPHEN, getDefinitionType().getName()));
		registryMap.removeAndPutAll(
				registryMap.keySet(),
				chooserDialogFactories.stream()
						.flatMap(this::definitionProviders)
						.collect(Collectors.toSet())
		);
	}

	private Stream<DefinitionProvider<DialogDefinition>> definitionProviders(final Class<?> factoryClass) {
		LOG.info("Registered dialog '{}' from {}", factoryClass.getSimpleName(), factoryClass.getName());
		final Object factory = Components.newInstance(factoryClass);
		return Stream.of(new ChooserDialogDefinitionProvider<>(factory));
	}

	@Override
	public DefinitionProvider<DialogDefinition> getProvider(final DefinitionMetadata id) {
		return registryMap.get(id);
	}

	@Override
	public DefinitionProvider<DialogDefinition> getProvider(final String id) {
		return registryMap.getByStringKey(id);
	}

	@Override
	public Collection<DefinitionMetadata> getAllMetadata() {
		return registryMap.keySet();
	}

	@Override
	public Collection<DefinitionProvider<DialogDefinition>> getAllProviders() {
		return registryMap.values();
	}

}

package com.merkle.oss.magnolia.templatebuilder;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;
import info.magnolia.rendering.template.registry.TemplateDefinitionRegistry;

import java.lang.invoke.MethodHandles;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateBuilderModule implements ModuleLifecycle {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TemplateDefinitionRegistry templateDefinitionRegistry;
    private final TemplateConfigurationSource templateConfigurationSource;

    @Inject
	public TemplateBuilderModule(
			final TemplateDefinitionRegistry templateDefinitionRegistry,
			final TemplateConfigurationSource templateConfigurationSource
	) {
        this.templateDefinitionRegistry = templateDefinitionRegistry;
        this.templateConfigurationSource = templateConfigurationSource;
    }

	@Override
	public void start(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Starting TemplateBuilder Module");
		templateDefinitionRegistry.bindTo(templateConfigurationSource);
		templateConfigurationSource.start();
	}

	@Override
	public void stop(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Stopping TemplateBuilder Module");
	}
}

package com.merkle.oss.magnolia.dialogbuilder;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

public class DialogBuilderModule implements ModuleLifecycle {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final DialogDefinitionRegistry dialogDefinitionRegistry;
    private final DialogConfigurationSource dialogConfigurationSource;

    @Inject
	public DialogBuilderModule(
			final DialogDefinitionRegistry dialogDefinitionRegistry,
			final DialogConfigurationSource dialogConfigurationSource
	) {
        this.dialogDefinitionRegistry = dialogDefinitionRegistry;
        this.dialogConfigurationSource = dialogConfigurationSource;
    }

	@Override
	public void start(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Starting DialogBuilder Module");
		dialogDefinitionRegistry.bindTo(dialogConfigurationSource);
		dialogConfigurationSource.start();
	}

	@Override
	public void stop(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Stopping DialogBuilder Module");
	}
}

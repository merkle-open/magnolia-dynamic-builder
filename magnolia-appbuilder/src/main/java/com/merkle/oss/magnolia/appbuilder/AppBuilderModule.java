package com.merkle.oss.magnolia.appbuilder;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;
import info.magnolia.ui.api.app.registry.AppDescriptorRegistry;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

public class AppBuilderModule implements ModuleLifecycle {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final DialogDefinitionRegistry dialogDefinitionRegistry;
    private final ChooserDialogConfigurationSource chooserDialogRegistrar;
    private final AppDescriptorRegistry appDescriptorRegistry;
    private final AppConfigurationSource appConfigurationSource;

    @Inject
	public AppBuilderModule(
			final AppDescriptorRegistry appDescriptorRegistry,
			final AppConfigurationSource appConfigurationSource,
			final DialogDefinitionRegistry dialogDefinitionRegistry,
			final ChooserDialogConfigurationSource chooserDialogRegistrar
	) {
        this.appDescriptorRegistry = appDescriptorRegistry;
        this.appConfigurationSource = appConfigurationSource;
        this.dialogDefinitionRegistry = dialogDefinitionRegistry;
        this.chooserDialogRegistrar = chooserDialogRegistrar;
    }

	@Override
	public void start(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Starting AppBuilder Module");
		appDescriptorRegistry.bindTo(appConfigurationSource);
		dialogDefinitionRegistry.bindTo(chooserDialogRegistrar);
		appConfigurationSource.start();
		chooserDialogRegistrar.start();
	}

	@Override
	public void stop(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Stopping AppBuilder Module");
	}
}

package com.namics.oss.magnolia.appbuilder;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;

import java.lang.invoke.MethodHandles;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppBuilderModule implements ModuleLifecycle {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final AppRegistrar appRegistrar;
    private final ChooserDialogRegistrar chooserDialogRegistrar;

    @Inject
	public AppBuilderModule(
			final AppRegistrar appRegistrar,
			final ChooserDialogRegistrar chooserDialogRegistrar
	) {
        this.appRegistrar = appRegistrar;
        this.chooserDialogRegistrar = chooserDialogRegistrar;
    }

	@Override
	public void start(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Starting AppBuilder Module");
		appRegistrar.register();
		chooserDialogRegistrar.register();
	}

	@Override
	public void stop(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Stopping AppBuilder Module");
	}
}

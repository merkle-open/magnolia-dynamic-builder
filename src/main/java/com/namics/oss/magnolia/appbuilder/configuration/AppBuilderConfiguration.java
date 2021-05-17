package com.namics.oss.magnolia.appbuilder.configuration;

import com.namics.oss.magnolia.appbuilder.AppExporter;
import com.namics.oss.magnolia.appbuilder.AppFactoryDescriptionBuilder;
import info.magnolia.admincentral.AdmincentralModule;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.app.launcherlayout.AppLauncherLayoutManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.invoke.MethodHandles;

@Configuration
public class AppBuilderConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Bean
	public AppExporter appExporter(AppFactoryDescriptionBuilder appFactoryDescriptionBuilder) {
		LOG.info("Creating AppExporter Bean");
		AppExporter appExporter = new AppExporter();
		appExporter.setAppFactoryDescriptionBuilder(appFactoryDescriptionBuilder);
		return appExporter;
	}

	@Bean
	public AppFactoryDescriptionBuilder appDescriptionBuilder() {
		LOG.info("Creating AppFactoryDescriptionBuilder Bean");
		return new AppFactoryDescriptionBuilder();
	}

	@Bean
	public AppLauncherLayoutManager appLauncherLayoutManager() {
		return Components.getComponent(AppLauncherLayoutManager.class);
	}

	@Bean
	public AdmincentralModule admincentralModule() {
		return Components.getComponent(AdmincentralModule.class);
	}
}

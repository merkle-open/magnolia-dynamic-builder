package com.namics.oss.magnolia.appbuilder.configuration;

import info.magnolia.admincentral.AdmincentralModule;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.app.launcherlayout.AppLauncherLayoutManager;
import info.magnolia.ui.api.app.registry.AppDescriptorRegistry;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(
		basePackages = {
				"com.namics.oss.magnolia.appbuilder"
		},
		includeFilters = {
				@ComponentScan.Filter(Component.class),
				@ComponentScan.Filter(Service.class),
		}
)
public class AppBuilderConfiguration {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public AppLauncherLayoutManager appLauncherLayoutManager() {
		return Components.getComponent(AppLauncherLayoutManager.class);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public AdmincentralModule admincentralModule() {
		return Components.getComponent(AdmincentralModule.class);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public AppDescriptorRegistry appDescriptorRegistry() {
		return Components.getComponent(AppDescriptorRegistry.class);
	}
}

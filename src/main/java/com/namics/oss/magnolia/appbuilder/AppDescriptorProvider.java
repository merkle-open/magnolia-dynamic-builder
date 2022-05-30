package com.namics.oss.magnolia.appbuilder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.config.registry.DefinitionRawView;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.ui.api.app.AppDescriptor;
import info.magnolia.ui.api.app.registry.DefinitionTypes;
import info.magnolia.ui.contentapp.ConfiguredContentAppDescriptor;
import info.magnolia.ui.contentapp.ContentApp;

import java.util.Collections;
import java.util.List;

public class AppDescriptorProvider implements DefinitionProvider<AppDescriptor> {

	private AppFactoryDescription appFactoryDescription;

	public AppDescriptorProvider(AppFactoryDescription appFactoryDescription) {
		this.appFactoryDescription = appFactoryDescription;
	}

	@Override
	public List<DefinitionDecorator<AppDescriptor>> getDecorators() {
		return Collections.emptyList();
	}

	@Override
	public DefinitionMetadata getMetadata() {
		AppDefinitionMetaDataBuilder appDefinitionMetaDataBuilder = new AppDefinitionMetaDataBuilder(appFactoryDescription.getId());
		appDefinitionMetaDataBuilder
				.type(DefinitionTypes.APP)
				.location(appFactoryDescription.getFactoryMetaData().getFactoryObject().getClass().getName());
		return appDefinitionMetaDataBuilder.build();
	}

	@Override
	public AppDescriptor get() throws Registry.InvalidDefinitionException {
		ConfiguredContentAppDescriptor configuredContentAppDescriptor = new ConfiguredContentAppDescriptor();
		configuredContentAppDescriptor.setAppClass(ContentApp.class);
		configuredContentAppDescriptor.setName(appFactoryDescription.getName());
		configuredContentAppDescriptor.setEnabled(appFactoryDescription.isEnabled());
		configuredContentAppDescriptor.setSubApps(appFactoryDescription.getSubApps());
		configuredContentAppDescriptor.setIcon(appFactoryDescription.getFactoryMetaData().getIcon());
		configuredContentAppDescriptor.setTheme(appFactoryDescription.getFactoryMetaData().getTheme());
		configuredContentAppDescriptor.setLabel(appFactoryDescription.getFactoryMetaData().getLabel());
		configuredContentAppDescriptor.setI18nBasename(appFactoryDescription.getFactoryMetaData().getI18nBasename());
		configuredContentAppDescriptor.setChooseDialog(appFactoryDescription.getChooseDialog());
		configuredContentAppDescriptor.setPermissions(appFactoryDescription.getPermissions());
		return configuredContentAppDescriptor;
	}

	@Override
	public DefinitionRawView getRaw() {
		return DefinitionRawView.EMPTY;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public long getLastModified() {
		return System.currentTimeMillis();
	}
}

package com.namics.oss.magnolia.appbuilder;

import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import info.magnolia.config.registry.DefinitionMetadataBuilder;
import info.magnolia.ui.api.app.registry.DefinitionTypes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;

public class AppDefinitionMetaDataBuilder extends DefinitionMetadataBuilder {
	private final String name;

	public AppDefinitionMetaDataBuilder(final Object appFactory) {
		final AppFactory appFactoryAnnotation = AopUtils
				.getTargetClass(appFactory)
				.getAnnotation(AppFactory.class);

		this.name = generateName(appFactoryAnnotation);
		type(DefinitionTypes.APP);
		location(generateLocation(appFactory));
		module(generateModule(appFactoryAnnotation));
		relativeLocation(generateRelativeLocation(appFactoryAnnotation));
		name(name);
	}

	@Override
	protected String buildReferenceId() {
		return name;
	}

	protected String generateLocation(final Object appFactory) {
		return appFactory.getClass().getName();
	}

	protected String generateModule(final AppFactory appFactoryAnnotation) {
		final String id = appFactoryAnnotation.id();
		return id.contains(":") ? StringUtils.substringBefore(id, ":") : null;
	}

	protected String generateRelativeLocation(final AppFactory appFactoryAnnotation) {
		final String id = appFactoryAnnotation.id();
		return id.contains(":") ? StringUtils.substringAfter(id, ":") : id;
	}

	protected String generateName(final AppFactory appFactoryAnnotation) {
		final String relativeLocation = generateRelativeLocation(appFactoryAnnotation);
		return relativeLocation.indexOf('/') != -1 ? StringUtils.substringAfterLast(relativeLocation, "/") : relativeLocation;
	}
}

package com.merkle.oss.magnolia.appbuilder;

import info.magnolia.cms.security.operations.AccessDefinition;
import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.app.AppDescriptor;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.api.app.registry.DefinitionTypes;
import info.magnolia.ui.contentapp.ContentApp;
import info.magnolia.ui.contentapp.configuration.ContentAppDescriptor;
import info.magnolia.ui.datasource.DatasourceDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.merkle.oss.magnolia.appbuilder.annotations.AppFactory;
import com.merkle.oss.magnolia.appbuilder.annotations.AppPermissions;
import com.merkle.oss.magnolia.appbuilder.annotations.CustomIcon;
import com.merkle.oss.magnolia.appbuilder.annotations.Icon;
import com.merkle.oss.magnolia.appbuilder.annotations.SubApp;
import com.merkle.oss.magnolia.builder.AbstractDynamicDefinitionProvider;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;
import com.merkle.oss.magnolia.builder.annotation.Unspecified;

import jakarta.inject.Provider;

public class AppDescriptorProvider extends AbstractDynamicDefinitionProvider<AppDescriptor> {
	private final DefinitionMetadata metadata;
	private final Provider<Object> factoryObjectProvider;
	private final AppFactory annotation;

	public AppDescriptorProvider(
			final List<DefinitionDecorator<AppDescriptor>> decorators,
			final Provider<Object> factoryObjectProvider,
			final Class<?> factoryClass
	) {
		super(decorators);
		this.factoryObjectProvider = factoryObjectProvider;
		this.annotation = factoryClass.getAnnotation(AppFactory.class);
		this.metadata = new AppDefinitionMetaDataBuilder(factoryClass, annotation.id())
				.type(DefinitionTypes.APP)
				.build();
	}

	@Override
	public DefinitionMetadata getMetadata() {
		return metadata;
	}

	@Override
	protected AppDescriptor getInternal() throws Registry.InvalidDefinitionException {
		final Object factoryObject = factoryObjectProvider.get();
		final ContentAppDescriptorWithId appDescriptor = new ContentAppDescriptorWithId(annotation.id());
		appDescriptor.setAppClass(ContentApp.class);
		appDescriptor.setName(annotation.name());
		appDescriptor.setEnabled(annotation.isEnabled());
		appDescriptor.setLabel(annotation.label());
		Unspecified.getValue(annotation.theme()).ifPresent(appDescriptor::setTheme);
		Unspecified.getValue(annotation.i18nBasename()).ifPresent(appDescriptor::setI18nBasename);

		try {
			getIcon(factoryObject).ifPresent(appDescriptor::setIcon);
			appDescriptor.setSubApps(getSubApps(factoryObject));
			getPermissions(factoryObject).ifPresent(appDescriptor::setPermissions);
		} catch (Exception e) {
			addProblem(e);
		}
		return appDescriptor;
	}

	private Optional<String> getIcon(final Object factoryObject) {
		if (factoryObject.getClass().isAnnotationPresent(Icon.class) && factoryObject.getClass().isAnnotationPresent(CustomIcon.class)) {
			throw new IllegalArgumentException("Either Icon or CustomIcon must be specified - not both!");
		}
		return Optional
				.ofNullable(factoryObject.getClass().getAnnotation(Icon.class)).map(Icon::value).map(MagnoliaIcons::getCssClass)
				.or(() ->
						Optional.ofNullable(factoryObject.getClass().getAnnotation(CustomIcon.class)).map(CustomIcon::value)
				)
				.or(() -> Unspecified.getValue(annotation.icon()));
	}

	private Map<String, SubAppDescriptor> getSubApps(final Object factoryObject) {
		return getDefinitions(factoryObject, SubApp.class, SubAppDescriptor.class).collect(Collectors.toMap(
				SubAppDescriptor::getName,
				Function.identity()
		));
	}

	private Optional<AccessDefinition> getPermissions(final Object factoryObject) {
		return getDefinitions(factoryObject, AppPermissions.class, AccessDefinition.class).findFirst();
	}

	private <T> Stream<T> getDefinitions(
			final Object factoryObject,
			final Class<? extends Annotation> annotation,
			final Class<T> definitionType
	) {

		return Arrays.stream(factoryObject.getClass().getMethods())
				.filter(method -> method.isAnnotationPresent(annotation))
				.map(method -> {
					try {
						//noinspection unchecked
						return (T)  method.invoke(factoryObject);
					} catch (IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException("Could not build " + definitionType.getSimpleName() + ", for app " + this.annotation.name(), e);
					}
				});
	}

	protected static class AppDefinitionMetaDataBuilder extends DynamicDefinitionMetaData.Builder {
		public AppDefinitionMetaDataBuilder(final Class<?> factoryClass, final String id) {
			super(factoryClass, id);
		}

		@Override
		protected String buildReferenceId() {
			return getName();
		}
	}

	public static class ContentAppDescriptorWithId extends ContentAppDescriptor<DatasourceDefinition> {
        private final String id;
        public ContentAppDescriptorWithId(final String id) {
            this.id = id;
        }
		public String getId() {
			return id;
		}
	}

	public static class Factory {
		public AppDescriptorProvider create(final Class<?> factoryClass) {
			return new AppDescriptorProvider(Collections.emptyList(), () -> Components.newInstance(factoryClass), factoryClass);
		}
	}
}

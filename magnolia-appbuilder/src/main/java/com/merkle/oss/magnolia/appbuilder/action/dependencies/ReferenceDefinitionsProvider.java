package com.merkle.oss.magnolia.appbuilder.action.dependencies;

import info.magnolia.config.NamedDefinition;
import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.dam.app.field.DamLinkFieldDefinition;
import info.magnolia.dam.jcr.DamConstants;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.module.dependencies.field.ConfiguredReferencesDefinition;
import info.magnolia.module.dependencies.field.ReferencesDefinition;
import info.magnolia.module.dependencies.field.factory.DamAppLocationFactoryImpl;
import info.magnolia.module.dependencies.field.resolver.AssetPathResolverImpl;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.ui.api.app.AppDescriptor;
import info.magnolia.ui.api.app.registry.AppDescriptorRegistry;
import info.magnolia.ui.contentapp.configuration.ContentSubAppDescriptor;
import info.magnolia.ui.contentapp.detail.DetailDescriptor;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;
import info.magnolia.ui.dialog.DialogDefinition;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;
import info.magnolia.ui.dialog.FormDialogDefinition;
import info.magnolia.ui.editor.FormDefinition;
import info.magnolia.ui.field.CompositeFieldDefinition;
import info.magnolia.ui.field.EditorPropertyDefinition;
import info.magnolia.ui.field.LinkFieldDefinition;
import info.magnolia.ui.field.MultiFieldDefinition;
import info.magnolia.ui.field.RichTextFieldDefinition;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.merkle.oss.magnolia.appbuilder.builder.browser.BrowserAppBuilder;
import com.merkle.oss.magnolia.appbuilder.util.Lazy;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class ReferenceDefinitionsProvider {
	private final Lazy<List<ReferencesDefinition>> definitionsProvider;

	@Inject
	public ReferenceDefinitionsProvider(
			final Provider<DialogDefinitionRegistry> dialogDefinitionRegistry,
			final Provider<AppDescriptorRegistry> appDescriptorRegistry
	) {
		this.definitionsProvider = Lazy.of(() -> get(dialogDefinitionRegistry.get(), appDescriptorRegistry.get()));
	}

	public List<ReferencesDefinition> get() {
		return definitionsProvider.get();
	}

	private List<ReferencesDefinition> get(
			final DialogDefinitionRegistry dialogDefinitionRegistry,
			final AppDescriptorRegistry appDescriptorRegistry
	) {
		final Set<EditorPropertyDefinition> definitions = getDefinitions(dialogDefinitionRegistry, appDescriptorRegistry);
		return Stream.concat(
				Stream.of(
						getWebsiteReferencesDefinition(definitions),
						getAssetReferencesDefinition(definitions)
				),
				streamAppDescriptors(appDescriptorRegistry).map(appDescriptor -> get(appDescriptor, definitions)).flatMap(Optional::stream)
		).toList();
	}
	private Optional<ReferencesDefinition> get(final AppDescriptor app, final Set<EditorPropertyDefinition> definitions) {
		return Optional.of(app)
				.map(AppDescriptor::getSubApps)
				.map(subApps -> subApps.get(BrowserAppBuilder.NAME))
				.filter(ContentSubAppDescriptor.class::isInstance)
				.map(ContentSubAppDescriptor.class::cast)
				.map(ContentSubAppDescriptor::getDatasource)
				.filter(JcrDatasourceDefinition.class::isInstance)
				.map(JcrDatasourceDefinition.class::cast)
				.map(datasource -> get(app, datasource, definitions));
	}

	protected ConfiguredReferencesDefinition get(final AppDescriptor app, final JcrDatasourceDefinition datasource, final Set<EditorPropertyDefinition> definitions) {
		final ConfiguredReferencesDefinition customAppReference = new ConfiguredReferencesDefinition();
		customAppReference.setName(app.getName());
		customAppReference.setLabel(app.getLabel());
		customAppReference.setNodeType(NodeTypes.Content.NAME);
		customAppReference.setAppMapping("app:" + app.getName() + ":" + BrowserAppBuilder.NAME);
		customAppReference.setWorkspace(datasource.getWorkspace());
		customAppReference.setReferenceProperties(getPropertyNames(
				definitions, definition ->
						isLinkToWorkspace(definition, datasource.getWorkspace())
		));
		return customAppReference;
	}

	protected ReferencesDefinition getWebsiteReferencesDefinition(final Set<EditorPropertyDefinition> definitions) {
		final ConfiguredReferencesDefinition websiteReference = new ConfiguredReferencesDefinition();
		websiteReference.setName("pages");
		websiteReference.setLabel("pages");
		websiteReference.setNodeType(NodeTypes.Page.NAME);
		websiteReference.setAppMapping("app:pages-app:browser");
		websiteReference.setWorkspace(RepositoryConstants.WEBSITE);
		websiteReference.setReferenceProperties(getPropertyNames(
				definitions, definition ->
						isLinkToWorkspace(definition, RepositoryConstants.WEBSITE) || isAnyType(definition, Set.of(RichTextFieldDefinition.class))
		));
		return websiteReference;
	}

	protected ReferencesDefinition getAssetReferencesDefinition(final Set<EditorPropertyDefinition> definitions) {
		final ConfiguredReferencesDefinition assetsReference = new ConfiguredReferencesDefinition();
		assetsReference.setName("assets");
		assetsReference.setLabel("assets");
		assetsReference.setAppMapping("app:dam:jcrBrowser");
		assetsReference.setWorkspace(DamConstants.WORKSPACE);
		assetsReference.setPathResolver(new AssetPathResolverImpl());
		assetsReference.setAppLocationFactory(new DamAppLocationFactoryImpl());
		assetsReference.setReferenceProperties(getPropertyNames(
				definitions, definition ->
						isAnyType(definition, Set.of(DamLinkFieldDefinition.class, RichTextFieldDefinition.class))
		));
		return assetsReference;
	}

	private boolean isAnyType(final EditorPropertyDefinition definition, final Set<Class<? extends EditorPropertyDefinition>> types) {
		return types.stream().anyMatch(clazz -> clazz.isAssignableFrom(definition.getClass()));
	}
	private boolean isLinkToWorkspace(final EditorPropertyDefinition definition, final String workspace) {
		return definition instanceof final LinkFieldDefinition<?> link &&
				link.getDatasource() instanceof final JcrDatasourceDefinition datasource &&
				Objects.equals(datasource.getWorkspace(), workspace);
	}
	private List<String> getPropertyNames(final Set<EditorPropertyDefinition> definitions, final Predicate<EditorPropertyDefinition> predicate) {
		return definitions.stream()
				.filter(predicate)
				.map(NamedDefinition::getName)
				.distinct()
				.collect(Collectors.toList());
	}

	private Set<EditorPropertyDefinition> getDefinitions(
			final DialogDefinitionRegistry dialogDefinitionRegistry,
			final AppDescriptorRegistry appDescriptorRegistry
	) {
		return Stream.concat(
				getDefinitions(dialogDefinitionRegistry),
				getDefinitions(appDescriptorRegistry)
		).collect(Collectors.toSet());
	}
	private Stream<EditorPropertyDefinition> getDefinitions(final AppDescriptorRegistry appDescriptorRegistry) {
		return streamAppDescriptors(appDescriptorRegistry)
				.map(AppDescriptor::getSubApps)
				.map(Map::values)
				.flatMap(Collection::stream)
				.filter(DetailDescriptor.class::isInstance)
				.map(DetailDescriptor.class::cast)
				.map(DetailDescriptor::getForm)
				.flatMap(this::streamDefinitions);
	}
	private Stream<EditorPropertyDefinition> getDefinitions(final DialogDefinitionRegistry dialogDefinitionRegistry) {
		return streamDialogDefinitions(dialogDefinitionRegistry)
				.filter(FormDialogDefinition.class::isInstance)
				.map(FormDialogDefinition.class::cast)
				.map(FormDialogDefinition::getForm)
				.flatMap(this::streamDefinitions);
	}
	private Stream<EditorPropertyDefinition> streamDefinitions(final FormDefinition<?> form) {
		return form.getProperties().stream().flatMap(this::streamDefinitions);
	}
	private Stream<EditorPropertyDefinition> streamDefinitions(final EditorPropertyDefinition property) {
		if (property instanceof final CompositeFieldDefinition<?> composite) {
			return composite.getProperties().stream().flatMap(this::streamDefinitions);
		}
		if (property instanceof final MultiFieldDefinition<?> multi) {
			return streamDefinitions(multi.getField());
		}
		return Stream.of(property);
	}

	private Stream<DialogDefinition> streamDialogDefinitions(final DialogDefinitionRegistry dialogDefinitionRegistry) {
		return dialogDefinitionRegistry
				.getAllProviders()
				.stream()
				.filter(this::dialogFilter)
				.map(DefinitionProvider::get);
	}

    private Stream<AppDescriptor> streamAppDescriptors(final AppDescriptorRegistry appDescriptorRegistry) {
        return appDescriptorRegistry
                .getAllProviders()
                .stream()
                .filter(this::appFilter)
                .map(DefinitionProvider::get);
    }

	protected boolean dialogFilter(final DefinitionProvider<DialogDefinition> dialogProvider) {
		return dialogProvider.getMetadata() instanceof DynamicDefinitionMetaData;
	}
	protected boolean appFilter(final DefinitionProvider<AppDescriptor> appProvider) {
		return appProvider.getMetadata() instanceof DynamicDefinitionMetaData;
	}
}

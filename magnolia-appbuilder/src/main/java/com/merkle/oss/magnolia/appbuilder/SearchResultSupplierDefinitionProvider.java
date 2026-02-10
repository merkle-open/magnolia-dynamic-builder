package com.merkle.oss.magnolia.appbuilder;

import com.merkle.oss.magnolia.appbuilder.annotations.AppFactory;
import com.merkle.oss.magnolia.appbuilder.annotations.CustomIcon;
import com.merkle.oss.magnolia.appbuilder.annotations.Icon;
import com.merkle.oss.magnolia.appbuilder.annotations.SearchResultSupplier;
import com.merkle.oss.magnolia.builder.AbstractDynamicDefinitionProvider;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;
import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.objectfactory.Components;
import info.magnolia.periscope.search.SearchResultSupplierDefinition;
import info.magnolia.periscope.search.SearchResultSupplierDefinitionRegistry;
import info.magnolia.periscope.search.jcr.JcrSearchResultSupplierDefinition;

import java.util.*;
import java.util.stream.Collectors;

import info.magnolia.periscope.search.jcr.JcrSearchResultSupplierOrder;
import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class SearchResultSupplierDefinitionProvider extends AbstractDynamicDefinitionProvider<SearchResultSupplierDefinition> {
    private final DefinitionMetadata metadata;
    private final Provider<Object> factoryObjectProvider;
    private final SearchResultSupplier annotation;

    @Inject
    public SearchResultSupplierDefinitionProvider(
            final List<DefinitionDecorator<SearchResultSupplierDefinition>> decorators,
            final Provider<Object> factoryObjectProvider,
            final Class<?> factoryClass
    ) {
        super(decorators);
        this.factoryObjectProvider = factoryObjectProvider;
        this.annotation = factoryClass.getAnnotation(SearchResultSupplier.class);
        this.metadata = new DynamicDefinitionMetaData.Builder(factoryClass, generateId())
                .type(SearchResultSupplierDefinitionRegistry.TYPE)
                .build();
    }

    private String generateId() {
        final String name = getName(factoryObjectProvider.get())
                .orElse(factoryObjectProvider.get().getClass().getSimpleName());
        return name + ":" + name;
    }

    @Override
    public DefinitionMetadata getMetadata() {
        return metadata;
    }

    @Override
    protected JcrSearchResultSupplierDefinition getInternal() throws Registry.InvalidDefinitionException {
        final Object factoryObject = factoryObjectProvider.get();
        final JcrSearchResultSupplierDefinition jcrSearchResultSupplierDefinition = new JcrSearchResultSupplierDefinition();
        jcrSearchResultSupplierDefinition.setWorkspace(annotation.workspace());
        jcrSearchResultSupplierDefinition.setEnabled(annotation.isEnabled());
        jcrSearchResultSupplierDefinition.setFullTextSearch(annotation.fullTextSearch());
        jcrSearchResultSupplierDefinition.setPropertySearch(annotation.propertySearch());
        jcrSearchResultSupplierDefinition.setRootPath(annotation.rootPath());
        jcrSearchResultSupplierDefinition.setResultLimit(annotation.resultLimit());
        jcrSearchResultSupplierDefinition.setTimeoutSeconds(annotation.timeoutSeconds());
        jcrSearchResultSupplierDefinition.setSubAppName(annotation.subAppName());
        jcrSearchResultSupplierDefinition.setDetailSubAppName(annotation.detailSuppAppName());
        jcrSearchResultSupplierDefinition.setNodeNameProperty(annotation.nodeNameProperty());
        final Set<String> nodeTypes = annotation.nodeTypes().length > 0 ?
                Arrays.stream(annotation.nodeTypes()).collect(Collectors.toSet()) : Collections.emptySet();
        jcrSearchResultSupplierDefinition.setNodeTypes(nodeTypes);
        getName(factoryObject).ifPresent(jcrSearchResultSupplierDefinition::setName);
        getAppName(factoryObject).ifPresent(jcrSearchResultSupplierDefinition::setAppName);
        getLabel(factoryObject).ifPresent(jcrSearchResultSupplierDefinition::setLabel);
        getIcon(factoryObject).ifPresent(jcrSearchResultSupplierDefinition::setIcon);
        getOrder(annotation.order()).ifPresent(jcrSearchResultSupplierDefinition::setOrder);

        return jcrSearchResultSupplierDefinition;
    }

    private Optional<String> getIcon(final Object factoryObject) {
        if (!SearchResultSupplier.UNDEFINED.equals(annotation.icon())){
            return Optional.of(annotation.icon());
        }
        return Optional
                .ofNullable(factoryObject.getClass().getAnnotation(Icon.class)).map(Icon::value).map(MagnoliaIcons::getCssClass)
                .or(() ->
                        Optional.ofNullable(factoryObject.getClass().getAnnotation(CustomIcon.class)).map(CustomIcon::value)
                );
    }

    private Optional<String> getName(final Object factoryObject) {
        if (!SearchResultSupplier.UNDEFINED.equals(annotation.name())) {
            return Optional.of(annotation.name());
        }
        return getAppNameFromAppFactory(factoryObject);
    }

    private Optional<String> getAppName(final Object factoryObject) {
        if (!SearchResultSupplier.UNDEFINED.equals(annotation.appName())) {
            return Optional.of(annotation.appName());
        }
        return getAppNameFromAppFactory(factoryObject);
    }

    private Optional<String> getLabel(final Object factoryObject) {
        if (!SearchResultSupplier.UNDEFINED.equals(annotation.label())) {
            return Optional.of(annotation.label());
        }
        return getAppNameFromAppFactory(factoryObject);
    }

    private Optional<String> getAppNameFromAppFactory(final Object factoryObject) {
        return Optional.ofNullable(factoryObject.getClass().getAnnotation(AppFactory.class)).map(AppFactory::name);
    }

    private Optional<JcrSearchResultSupplierOrder> getOrder(final SearchResultSupplier.Order orderAnnotation) {
        if (!SearchResultSupplier.UNDEFINED.equals(orderAnnotation.property())) {
            final JcrSearchResultSupplierOrder jcrSearchResultSupplierOrder = new JcrSearchResultSupplierOrder();
            jcrSearchResultSupplierOrder.setProperty(orderAnnotation.property());
            jcrSearchResultSupplierOrder.setDirection(orderAnnotation.direction());
            return Optional.of(jcrSearchResultSupplierOrder);
        }
        return Optional.empty();
    }


    public static class Factory {
        public SearchResultSupplierDefinitionProvider create(final Class<?> factoryClass) {
            return new SearchResultSupplierDefinitionProvider(Collections.emptyList(), () -> Components.newInstance(factoryClass), factoryClass);
        }
    }

}

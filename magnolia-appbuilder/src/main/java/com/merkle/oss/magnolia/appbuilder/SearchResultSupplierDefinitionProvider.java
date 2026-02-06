package com.merkle.oss.magnolia.appbuilder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.objectfactory.Components;
import info.magnolia.periscope.search.SearchResultSupplierDefinition;
import info.magnolia.periscope.search.SearchResultSupplierDefinitionRegistry;
import info.magnolia.periscope.search.jcr.JcrSearchResultSupplierDefinition;
import info.magnolia.periscope.search.jcr.JcrSearchResultSupplierOrder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.merkle.oss.magnolia.appbuilder.annotations.AppFactory;
import com.merkle.oss.magnolia.appbuilder.annotations.CustomIcon;
import com.merkle.oss.magnolia.appbuilder.annotations.Icon;
import com.merkle.oss.magnolia.appbuilder.annotations.SearchResultSupplier;
import com.merkle.oss.magnolia.builder.AbstractDynamicDefinitionProvider;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;
import com.merkle.oss.magnolia.builder.annotation.TernaryBoolean;
import com.merkle.oss.magnolia.builder.annotation.Unspecified;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class SearchResultSupplierDefinitionProvider extends AbstractDynamicDefinitionProvider<SearchResultSupplierDefinition> {
    private final DefinitionMetadata metadata;
    private final Class<?> factoryClass;
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
        this.factoryClass = factoryClass;
        this.annotation = factoryClass.getAnnotation(SearchResultSupplier.class);
        this.metadata = new SearchResultSupplierDefinitionMetaDataBuilder(
                factoryClass,
                getId().orElseThrow(() ->
                    new NullPointerException("id not present!")
                ),
                getAppName().orElseThrow(() ->
                    new NullPointerException("appName not present!")
                )
        ).type(SearchResultSupplierDefinitionRegistry.TYPE).build();
    }

    @Override
    public DefinitionMetadata getMetadata() {
        return metadata;
    }

    @Override
    protected JcrSearchResultSupplierDefinition getInternal() throws Registry.InvalidDefinitionException {
        final Object factoryObject = factoryObjectProvider.get();
        final JcrSearchResultSupplierDefinition definition = new JcrSearchResultSupplierDefinition();
        definition.setWorkspace(annotation.workspace());

        if(annotation.isEnabled() != TernaryBoolean.UNSPECIFIED) {
            definition.setEnabled(annotation.isEnabled() == TernaryBoolean.TRUE);
        }
        if(annotation.fullTextSearch() != TernaryBoolean.UNSPECIFIED) {
            definition.setFullTextSearch(annotation.fullTextSearch() == TernaryBoolean.TRUE);
        }
        if(annotation.propertySearch() != TernaryBoolean.UNSPECIFIED) {
            definition.setPropertySearch(annotation.propertySearch() == TernaryBoolean.TRUE);
        }
        getValue(annotation.rootPath()).ifPresent(definition::setRootPath);
        getValue(annotation.resultLimit()).ifPresent(definition::setResultLimit);
        getValue(annotation.timeoutSeconds()).ifPresent(definition::setTimeoutSeconds);
        getValue(annotation.subAppName()).ifPresent(definition::setSubAppName);
        getValue(annotation.detailSuppAppName()).ifPresent(definition::setDetailSubAppName);
        getValue(annotation.nodeNameProperty()).ifPresent(definition::setNodeNameProperty);
        definition.setNodeTypes(Arrays.stream(annotation.nodeTypes()).collect(Collectors.toSet()));
        final String appName = getAppName().orElseThrow(() ->
                new NullPointerException("App name not present!")
        );
        definition.setAppName(appName);
        // info.magnolia.apps.api.service.AppSearchSupplierService.groupSuppliersByAppId uses name to match corresponding app!? Also, this is done by name, not id!
        definition.setName(appName);
        getLabel().ifPresent(definition::setLabel);
        getIcon(factoryObject).ifPresent(definition::setIcon);
        getOrder(annotation.order()).ifPresent(definition::setOrder);
        return definition;
    }

    private Optional<String> getIcon(final Object factoryObject) {
        return getValue(annotation.icon())
                .or(() ->
                        Optional.ofNullable(factoryObject.getClass().getAnnotation(Icon.class)).map(Icon::value).map(MagnoliaIcons::getCssClass)
                )
                .or(() ->
                        Optional.ofNullable(factoryObject.getClass().getAnnotation(CustomIcon.class)).map(CustomIcon::value)
                );
    }

    private Optional<String> getId() {
        return getValue(annotation.id(), AppFactory::id);
    }
    private Optional<String> getAppName() {
        return getValue(annotation.appName(), AppFactory::name);
    }
    private Optional<String> getLabel() {
        return getValue(annotation.label(), AppFactory::name);
    }

    private Optional<JcrSearchResultSupplierOrder> getOrder(final SearchResultSupplier.Order orderAnnotation) {
        final JcrSearchResultSupplierOrder jcrSearchResultSupplierOrder = new JcrSearchResultSupplierOrder();
        getValue(orderAnnotation.property()).ifPresent(jcrSearchResultSupplierOrder::setProperty);
        Optional.of(orderAnnotation.direction()).flatMap(this::map).ifPresent(jcrSearchResultSupplierOrder::setDirection);
        return Optional.of(jcrSearchResultSupplierOrder).filter(order ->
                order.getDirection() != null || order.getProperty() != null
        );
    }
    private Optional<JcrSearchResultSupplierOrder.Direction> map(final SearchResultSupplier.Order.Direction direction) {
        return switch (direction){
            case ASC -> Optional.of(JcrSearchResultSupplierOrder.Direction.ASC);
            case DESC -> Optional.of(JcrSearchResultSupplierOrder.Direction.DESC);
            case UNSPECIFIED -> Optional.empty();
        };
    }

    private Optional<Integer> getValue(final int value) {
        return Optional.of(value).filter(v -> Unspecified.INT != v);
    }
    private Optional<String> getValue(final String value) {
        return Optional.of(value).filter(Predicate.not(Unspecified.STRING::equals));
    }
    private Optional<String> getValue(final String value, final Function<AppFactory, String> fallback) {
        return getValue(value).or(() ->
                Optional.ofNullable(factoryClass.getAnnotation(AppFactory.class)).map(fallback)
        );
    }

    protected static class SearchResultSupplierDefinitionMetaDataBuilder extends DynamicDefinitionMetaData.Builder {
        private final String appName;

        public SearchResultSupplierDefinitionMetaDataBuilder(final Class<?> factoryClass, final String id, final String appName) {
            super(factoryClass, id);
            this.appName = appName;
            module(generateModule(id));
        }

        /*
         * info.magnolia.apps.api.service.AppSearchSupplierService.groupSuppliersByAppId groups suppliers by module and returns only 1 app per module!?
         *  --> this needs to be unique per app
          */
        @Override
        protected String generateModule(final String id) {
            return super.generateModule(id) + "_" + appName;
        }
    }

    public static class Factory {
        public SearchResultSupplierDefinitionProvider create(final Class<?> factoryClass) {
            return new SearchResultSupplierDefinitionProvider(Collections.emptyList(), () -> Components.newInstance(factoryClass), factoryClass);
        }
    }
}

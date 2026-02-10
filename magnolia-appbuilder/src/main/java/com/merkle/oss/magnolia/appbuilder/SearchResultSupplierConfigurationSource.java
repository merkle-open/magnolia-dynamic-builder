package com.merkle.oss.magnolia.appbuilder;

import com.merkle.oss.magnolia.appbuilder.annotations.SearchResultSuppliers;
import com.merkle.oss.magnolia.builder.AbstractDynamicConfigurationSource;
import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.periscope.search.SearchResultSupplierDefinition;
import info.magnolia.periscope.search.SearchResultSupplierDefinitionRegistry;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.stream.Stream;

public class SearchResultSupplierConfigurationSource extends AbstractDynamicConfigurationSource<SearchResultSupplierDefinition> {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SearchResultSupplierDefinitionProvider.Factory searchResultSupplierDefinitionFactory;

    @Inject
	public SearchResultSupplierConfigurationSource(
			@SearchResultSuppliers final Set<Class<?>> searchResultSuppliers,
			final SearchResultSupplierDefinitionProvider.Factory searchResultSupplierDefinitionFactory
	) {
		super(SearchResultSupplierDefinitionRegistry.TYPE, searchResultSuppliers);
        this.searchResultSupplierDefinitionFactory = searchResultSupplierDefinitionFactory;
    }

	@Override
	protected Stream<DefinitionProvider<SearchResultSupplierDefinition>> definitionProviders(final Class<?> factoryClass) {
		LOG.info("Registered search result supplier '{}' from {}", factoryClass.getSimpleName(), factoryClass.getName());
		return Stream.of(searchResultSupplierDefinitionFactory.create(factoryClass));
	}
}

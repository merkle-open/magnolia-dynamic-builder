package com.merkle.oss.magnolia.appbuilder.builder.detail.parameter;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.contentapp.JcrNodeResolver;
import info.magnolia.ui.contentapp.detail.ContentDetailSubApp;
import info.magnolia.ui.datasource.DatasourceDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasource;
import info.magnolia.ui.editor.ItemProviderStrategy;

import java.util.Optional;

import javax.jcr.Item;
import javax.jcr.Node;

import com.merkle.oss.magnolia.appbuilder.builder.detail.ExtendedDetailLocation;
import com.merkle.oss.magnolia.appbuilder.builder.detail.JcrNodeFromLocationItemProviderStrategy;

import jakarta.inject.Provider;

public class LocationBasedNodeProvider implements Provider<Optional<Node>> {
    private final ItemProviderStrategy<Item, ContentDetailSubApp.DetailLocation> itemProviderStrategy;

    public LocationBasedNodeProvider(
            final DatasourceDefinition datasourceDefinition,
            final ComponentProvider componentProvider
    ) {
        final JcrNodeFromLocationItemProviderStrategy.Definition definition = new JcrNodeFromLocationItemProviderStrategy.Definition(false);
        final JcrDatasource jcrDatasource = componentProvider.newInstance(JcrDatasource.class, datasourceDefinition);
        final JcrNodeResolver jcrNodeResolver = componentProvider.newInstance(JcrNodeResolver.class, jcrDatasource);
        this.itemProviderStrategy = componentProvider.newInstance(definition.getImplementationClass(), definition, jcrNodeResolver);
    }

    @Override
    public Optional<Node> get() {
        final ExtendedDetailLocation location = ExtendedDetailLocation.wrap(Components.getComponent(LocationController.class).getWhere());
        return itemProviderStrategy.read(location).map(item -> (Node)item);
    }
}

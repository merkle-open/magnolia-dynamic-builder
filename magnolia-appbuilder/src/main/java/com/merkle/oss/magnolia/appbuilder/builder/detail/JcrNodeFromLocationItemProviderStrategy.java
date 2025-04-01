package com.merkle.oss.magnolia.appbuilder.builder.detail;

import static info.magnolia.ui.contentapp.detail.ContentDetailSubApp.*;

import info.magnolia.ui.contentapp.detail.ContentDetailSubApp;
import info.magnolia.ui.contentapp.version.VersionResolver;
import info.magnolia.ui.datasource.ItemResolver;
import info.magnolia.ui.editor.ItemProviderDefinition;
import info.magnolia.ui.editor.ItemProviderStrategy;

import java.util.Optional;

import javax.inject.Inject;
import javax.jcr.Item;

public class JcrNodeFromLocationItemProviderStrategy implements ItemProviderStrategy<Item, ContentDetailSubApp.DetailLocation> {
    private final Definition definition;
    private final ItemResolver<Item> itemResolver;
    private final VersionResolver<Item> versionResolver;

    @Inject
    public JcrNodeFromLocationItemProviderStrategy(
            final Definition definition,
            final ItemResolver<Item> itemResolver,
            final VersionResolver<Item> versionResolver
    ) {
        this.definition = definition;
        this.itemResolver = itemResolver;
        this.versionResolver = versionResolver;
    }

    @Override
    public Optional<Item> read(final ContentDetailSubApp.DetailLocation location) {
        return resolveNode(location);
    }

    private Optional<Item> resolveNode(final ContentDetailSubApp.DetailLocation location) {
        final String nodePath = location.getNodePath();
        return switch (location.getViewType()) {
            case VIEW_TYPE_ADD -> definition.isEmptyForAdd() ? Optional.empty() : itemResolver.getItemById(nodePath);
            case VIEW_TYPE_VERSION -> versionResolver.getVersion(nodePath, location.getVersion());
            default -> itemResolver.getItemById(nodePath);
        };
    }

    public static class Definition extends ItemProviderDefinition<Item, ContentDetailSubApp.DetailLocation> {
        private final boolean emptyForAdd;

        public Definition(final boolean emptyForAdd) {
            this.emptyForAdd = emptyForAdd;
            setImplementationClass(JcrNodeFromLocationItemProviderStrategy.class);
        }

        public boolean isEmptyForAdd() {
            return emptyForAdd;
        }
    }
}

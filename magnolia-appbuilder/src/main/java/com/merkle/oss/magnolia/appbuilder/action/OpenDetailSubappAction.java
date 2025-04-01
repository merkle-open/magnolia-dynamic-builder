package com.merkle.oss.magnolia.appbuilder.action;

import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.contentapp.action.OpenDetailSubappActionDefinition;
import info.magnolia.ui.contentapp.detail.ContentDetailSubApp;
import info.magnolia.ui.datasource.ItemResolver;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jcr.Node;

import com.merkle.oss.magnolia.appbuilder.action.add.NodeNameProvider;
import com.merkle.oss.magnolia.appbuilder.builder.detail.ExtendedDetailLocation;

public class OpenDetailSubappAction extends info.magnolia.ui.contentapp.action.OpenDetailSubappAction<Node> {
    private final Definition definition;

    @Inject
    public OpenDetailSubappAction(
            final Definition definition,
            final ValueContext<Node> valueContext,
            final LocationController locationController,
            final ItemResolver<Node> itemResolver
    ) {
        super(definition, valueContext, locationController, itemResolver);
        this.definition = definition;
    }

    @Override
    protected ContentDetailSubApp.DetailLocation getLocation(final Optional<Node> item, final ItemResolver<Node> itemResolver) {
        return new ExtendedDetailLocation(
                super.getLocation(item, itemResolver),
                definition.getNodeType(),
                definition.getNodeNameProviderClass()
        );
    }

    public static class Definition extends OpenDetailSubappActionDefinition {
        @Nullable
        private String nodeType;
        @Nullable
        private Class<? extends NodeNameProvider> nodeNameProviderClass;

        public Definition(final String appName, final String subAppName) {
            setImplementationClass(OpenDetailSubappAction.class);
            setAppName(appName);
            setSubAppName(subAppName);
        }

        @Nullable
        public String getNodeType() {
            return nodeType;
        }

        public void setNodeType(final String nodeType) {
            this.nodeType = nodeType;
        }

        @Nullable
        public Class<? extends NodeNameProvider> getNodeNameProviderClass() {
            return nodeNameProviderClass;
        }

        public void setNodeNameProviderClass(final Class<? extends NodeNameProvider> nodeNameProviderClass) {
            this.nodeNameProviderClass = nodeNameProviderClass;
        }
    }
}

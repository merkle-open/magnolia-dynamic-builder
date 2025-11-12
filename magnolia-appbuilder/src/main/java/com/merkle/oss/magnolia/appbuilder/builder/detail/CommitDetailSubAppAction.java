package com.merkle.oss.magnolia.appbuilder.builder.detail;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.CloseHandler;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.app.AppContext;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.contentapp.ContentBrowserSubApp;
import info.magnolia.ui.contentapp.Datasource;
import info.magnolia.ui.contentapp.action.CommitAction;
import info.magnolia.ui.contentapp.detail.ContentDetailSubApp;
import info.magnolia.ui.contentapp.detail.action.SaveDetailSubAppActionDefinition;
import info.magnolia.ui.datasource.ItemResolver;
import info.magnolia.ui.editor.EditorView;
import info.magnolia.ui.observation.DatasourceObservation;

import java.util.Objects;

import javax.jcr.Node;

import com.merkle.oss.magnolia.appbuilder.action.add.CreateNodeAction;
import com.merkle.oss.magnolia.appbuilder.builder.browser.BrowserAppBuilder;

import jakarta.inject.Inject;

public class CommitDetailSubAppAction extends CommitAction<Node> {
    private final Definition definition;
    private final LocationController locationController;
    private final AppContext appContext;
    private final ItemResolver<Node> itemResolver;
    private final ComponentProvider componentProvider;

    @Inject
    public CommitDetailSubAppAction(
            final Definition definition,
            final CloseHandler closeHandler,
            final ValueContext<Node> valueContext,
            final EditorView<Node> form,
            final Datasource<Node> datasource,
            final DatasourceObservation.Manual datasourceObservation,
            final LocationController locationController,
            final AppContext appContext,
            final ItemResolver<Node> itemResolver,
            final ComponentProvider componentProvider
    ) {
        super(definition, closeHandler, valueContext, form, datasource, datasourceObservation);
        this.definition = definition;
        this.locationController = locationController;
        this.appContext = appContext;
        this.itemResolver = itemResolver;
        this.componentProvider = componentProvider;
    }

    @Override
    public void execute() throws ActionExecutionException {
        if (validateForm()) {
            final ExtendedDetailLocation location = ExtendedDetailLocation.wrap(locationController.getWhere());
            if (Objects.equals(location.getViewType(), ContentDetailSubApp.VIEW_TYPE_ADD)) {
                final CreateNodeAction.Definition createNodeActionDefinition = new CreateNodeAction.Definition(
                        location.getNodeType().orElseThrow(() -> new NullPointerException("nodeType not present!")),
                        location.getNodeNameProviderClass().orElseThrow(() -> new NullPointerException("nodeNameProviderClass not present!"))
                );
                componentProvider.newInstance(createNodeActionDefinition.getImplementationClass(), createNodeActionDefinition).execute();
            } else {
                super.execute();
            }
            if (!definition.getBrowserName().isEmpty() && getForm().isValid()) {
                locationController.goTo(new ContentBrowserSubApp.BrowserLocation(
                        appContext.getName(),
                        definition.getBrowserName(),
                        getValueContext().getSingle()
                                .map(itemResolver::getId)
                                .orElse("")
                ));
            }
        }
    }

    public static class Definition extends SaveDetailSubAppActionDefinition {
        public Definition() {
            setImplementationClass(CommitDetailSubAppAction.class);
            setBrowserName(BrowserAppBuilder.NAME);
        }
    }
}

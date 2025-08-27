package com.merkle.oss.magnolia.appbuilder.builder.detail;

import static info.magnolia.ui.contentapp.action.CloseActionDefinition.CLOSE_ACTION_NAME;
import static info.magnolia.ui.contentapp.action.CommitActionDefinition.COMMIT_ACTION_NAME;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.contentapp.action.CloseActionDefinition;
import info.magnolia.ui.contentapp.detail.ContentDetailSubApp;
import info.magnolia.ui.contentapp.detail.DetailDescriptor;
import info.magnolia.ui.datasource.DatasourceDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;
import info.magnolia.ui.editor.FormDefinition;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.inject.Inject;
import javax.jcr.Item;

import com.merkle.oss.magnolia.appbuilder.JcrNameValidationAppender;
import com.merkle.oss.magnolia.appbuilder.builder.detail.parameter.DetailAppParameterResolverFactory;
import com.merkle.oss.magnolia.appbuilder.builder.detail.parameter.LocationBasedNodeProvider;
import com.merkle.oss.magnolia.definition.builder.datasource.JcrDatasourceDefinitionBuilder;
import com.merkle.oss.magnolia.formbuilder.FormFactory;

public class DetailAppBuilder {
    private final DetailAppParameterResolverFactory parameterResolverFactory;
    private final FormFactory.TabComparatorFactory tabComparatorFactory;
    private final JcrNameValidationAppender jcrNameValidationAppender;
    private final ComponentProvider componentProvider;

    private Function<Class<?>, Object> factoryObjectProvider = Components::newInstance;

    public DetailAppBuilder(
            final DetailAppParameterResolverFactory parameterResolverFactory,
            final FormFactory.TabComparatorFactory tabComparatorFactory,
            final JcrNameValidationAppender jcrNameValidationAppender,
            final ComponentProvider componentProvider
    ) {
        this.parameterResolverFactory = parameterResolverFactory;
        this.tabComparatorFactory = tabComparatorFactory;
        this.jcrNameValidationAppender = jcrNameValidationAppender;
        this.componentProvider = componentProvider;
    }

    public DetailAppBuilder factoryObjectProvider(final Function<Class<?>, Object> factoryObjectProvider) {
        this.factoryObjectProvider = factoryObjectProvider;
        return this;
    }

    public DetailDescriptor<Item, DatasourceDefinition> build(final Class<?> detailFactory, final String name, final String workspace) {
        final JcrDatasourceDefinition datasourceDefinition = new JcrDatasourceDefinitionBuilder()
                .workspace(workspace)
                .describeByProperty("jcrName") //see info.magnolia.ui.contentapp.JcrItemDescriber
                .build();
        return build(detailFactory, name, datasourceDefinition);
    }
    public DetailDescriptor<Item, DatasourceDefinition> build(final Class<?> detailFactory, final String name, final DatasourceDefinition datasourceDefinition) {
        final DetailSubApp.Definition definition = new DetailSubApp.Definition(
                () -> {
                    final ExtendedDetailLocation location = ExtendedDetailLocation.wrap(Components.getComponent(LocationController.class).getWhere());
                    return Objects.equals(location.getViewType(), ContentDetailSubApp.VIEW_TYPE_ADD);
                },
                detailSubAppDefinition -> {
                    final ExtendedDetailLocation location = ExtendedDetailLocation.wrap(Components.getComponent(LocationController.class).getWhere());
                    final FormFactory formFactory = new FormFactory(
                            (context, formDefinition) -> parameterResolverFactory.create(context, formDefinition, detailSubAppDefinition),
                            tabComparatorFactory,
                            new LocationBasedNodeProvider(datasourceDefinition, location, componentProvider)
                    );
                    final FormDefinition<Item> form = formFactory.create(factoryObjectProvider.apply(detailFactory));
                    final NodeNameValidatorDefinition.Mode mode = detailSubAppDefinition.isNewNode() ? NodeNameValidatorDefinition.Mode.ADD : NodeNameValidatorDefinition.Mode.EDIT;
                    jcrNameValidationAppender.addNodeNameValidatorToJcrNameField(form, mode);
                    return form;
                });
        definition.setSubAppClass(DetailSubApp.class);
        definition.setName(name);
        definition.setDatasource(datasourceDefinition);
        definition.setItemProvider(new JcrNodeFromLocationItemProviderStrategy.Definition(true));
        definition.setActions(Map.of(
                COMMIT_ACTION_NAME, new CommitDetailSubAppAction.Definition(),
                CLOSE_ACTION_NAME, new CloseActionDefinition()
        ));
        return definition;
    }

    public static class Factory {
        private final DetailAppParameterResolverFactory parameterResolverFactory;
        private final FormFactory.TabComparatorFactory tabComparatorFactory;
        private final JcrNameValidationAppender jcrNameValidationAppender;
        private final ComponentProvider componentProvider;

        @Inject
        public Factory(
                final DetailAppParameterResolverFactory parameterResolverFactory,
                final FormFactory.TabComparatorFactory tabComparatorFactory,
                final JcrNameValidationAppender jcrNameValidationAppender,
                final ComponentProvider componentProvider
        ) {
            this.parameterResolverFactory = parameterResolverFactory;
            this.tabComparatorFactory = tabComparatorFactory;
            this.jcrNameValidationAppender = jcrNameValidationAppender;
            this.componentProvider = componentProvider;
        }

        public DetailAppBuilder create() {
            return new DetailAppBuilder(
                    parameterResolverFactory,
                    tabComparatorFactory,
                    jcrNameValidationAppender,
                    componentProvider
            );
        }
    }
}

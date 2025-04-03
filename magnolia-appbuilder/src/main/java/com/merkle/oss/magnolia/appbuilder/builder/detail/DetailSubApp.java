package com.merkle.oss.magnolia.appbuilder.builder.detail;

import info.magnolia.config.NamedDefinition;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.api.i18n.I18NAuthoringSupport;
import info.magnolia.ui.api.location.Location;
import info.magnolia.ui.contentapp.ItemDescriber;
import info.magnolia.ui.contentapp.detail.DetailDescriptor;
import info.magnolia.ui.datasource.DatasourceDefinition;
import info.magnolia.ui.datasource.ItemResolver;
import info.magnolia.ui.editor.FormDefinition;
import info.magnolia.ui.editor.LocaleContext;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.inject.Inject;
import javax.jcr.Item;

public class DetailSubApp extends info.magnolia.ui.contentapp.detail.ContentDetailSubApp<Item> {
    private final ItemResolver<Item> itemResolver;
    private final LocaleContext localeContext;
    private final ValueContext<Item> valueContext;
    private final I18NAuthoringSupport<Item> i18NAuthoringSupport;

    @Inject
    public DetailSubApp(
            final SubAppContext subAppContext,
            final I18NAuthoringSupport<Item> i18NAuthoringSupport,
            final Definition definition,
            final LocaleContext localeContext,
            final ValueContext<Item> valueContext,
            final ItemDescriber<Item> itemDescriber,
            final ItemResolver<Item> itemResolver
    ) {
        super(subAppContext, i18NAuthoringSupport, definition, localeContext, valueContext, itemDescriber);
        this.itemResolver = itemResolver;
        this.localeContext = localeContext;
        this.valueContext = valueContext;
        this.i18NAuthoringSupport = i18NAuthoringSupport;
    }

    @Override
    public SubAppView start(final Location location) {
        final Item item = getItem(location).orElse(null);
        Optional.ofNullable(item).ifPresent(it -> {
            localeContext.populateFromI18NAuthoringSupport(it, i18NAuthoringSupport);
        });
        final SubAppView view = super.start(location);
        Optional.ofNullable(item).ifPresent(valueContext::set);
        return view;
    }

    private Optional<Item> getItem(final Location location) {
        final ExtendedDetailLocation detailLocation = ExtendedDetailLocation.wrap(location);
        if(Objects.equals(VIEW_TYPE_ADD, detailLocation.getViewType())) {
            return itemResolver.getItemById(detailLocation.getNodePath());
        }
        return Optional.empty();
    }

    public static class Definition extends DetailDescriptor<Item, DatasourceDefinition> implements NamedDefinition {
        private final Function<Definition, FormDefinition<Item>> formDefinitionProvider;

        public Definition(final Function<Definition, FormDefinition<Item>> formDefinitionProvider) {
            this.formDefinitionProvider = formDefinitionProvider;
            setSubAppClass(DetailSubApp.class);
        }

        @Override
        public FormDefinition<Item> getForm() {
            return formDefinitionProvider.apply(this);
        }
    }
}

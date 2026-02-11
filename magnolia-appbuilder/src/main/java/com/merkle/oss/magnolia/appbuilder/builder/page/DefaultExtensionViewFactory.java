package com.merkle.oss.magnolia.appbuilder.builder.page;

import info.magnolia.pages.app.detail.extension.LanguageSelectorViewDefinition;
import info.magnolia.pages.app.detail.extension.PlatformSelectorViewDefinition;
import info.magnolia.pages.app.detail.extension.PublishingStatusViewDefinition;
import info.magnolia.ui.UIComponent;
import info.magnolia.ui.ViewDefinition;

import java.util.List;
import java.util.function.Function;

public class DefaultExtensionViewFactory implements Function<Boolean, List<ViewDefinition<? extends UIComponent>>> {

    public List<ViewDefinition<? extends UIComponent>> apply(final Boolean multiTree) {
        return List.of(
                new PublishingStatusViewDefinition(),
                new PlatformSelectorViewDefinition(),
                multiTree ? new MultiTreeLanguageSelectorView.Definition() : new LanguageSelectorViewDefinition()
        );
    }
}

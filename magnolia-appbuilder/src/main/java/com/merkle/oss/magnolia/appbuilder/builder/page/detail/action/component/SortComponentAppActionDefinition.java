package com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import info.magnolia.pages.app.detail.action.SortComponentActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

public class SortComponentAppActionDefinition implements AppActionDefinition {

    @Override
    public SortComponentActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final SortComponentActionDefinition definition = new SortComponentActionDefinition();
        definition.setName("sortComponent");
        return definition;
    }

    @Override
    public boolean isCallback() {
        return true;
    }
}

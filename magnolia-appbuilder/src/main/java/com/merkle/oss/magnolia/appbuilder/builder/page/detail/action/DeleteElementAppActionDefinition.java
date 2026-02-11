package com.merkle.oss.magnolia.appbuilder.builder.page.detail.action;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import info.magnolia.pages.app.detail.action.DeleteElementActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

public class DeleteElementAppActionDefinition implements AppActionDefinition {
    public static final String NAME = "delete";
    @Override
    public DeleteElementActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final DeleteElementActionDefinition definition = new DeleteElementActionDefinition();
        definition.setName(NAME);
        return definition;
    }

    @Override
    public boolean isCallback() {
        return true;
    }
}

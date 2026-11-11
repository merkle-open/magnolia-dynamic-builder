package com.merkle.oss.magnolia.appbuilder.action.dependencies.show;

import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;

public class ShowDependenciesDialogAppActionDefinition implements AppActionDefinition {
    @Override
    public ConfiguredActionDefinition action(final DropConstraintDefinition dropConstraint) {
        return new GenericViewLinksActionDefinition();
    }
}

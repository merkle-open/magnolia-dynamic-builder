package com.merkle.oss.magnolia.appbuilder.builder.page.detail.action;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.OpenDialogAction;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.pages.app.detail.action.EditElementAction;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;

public class EditElementAppActionDefinition implements AppActionDefinition {

    @Override
    public OpenDialogAction.Definition action(final DropConstraintDefinition dropConstraint) {
        final OpenDialogAction.Definition definition = new OpenDialogAction.Definition(NodeNameValidatorDefinition.Mode.EDIT);
        definition.setIcon(MagnoliaIcons.EDIT.getCssClass());
        definition.setImplementationClass(EditElementAction.class);
        return definition;
    }
}

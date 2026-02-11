package com.merkle.oss.magnolia.appbuilder.builder.page.browser.action;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.OpenDialogAction;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;

public class AddPageAppActionDefinition implements AppActionDefinition {

    @Override
    public OpenDialogAction.Definition action(final DropConstraintDefinition dropConstraint) {
        final OpenDialogAction.Definition definition = new OpenDialogAction.Definition(NodeNameValidatorDefinition.Mode.ADD);
        definition.setName("addPage");
        definition.setDialogId("pages-app:createPage");
        definition.setIcon(MagnoliaIcons.ADD_ITEM.getCssClass());
        return definition;
    }
}

package com.merkle.oss.magnolia.appbuilder.builder.page.browser.action;

import info.magnolia.icons.MagnoliaIcons;

import com.merkle.oss.magnolia.appbuilder.action.edit.EditDetailAppActionDefinition;

public class EditDetailPageAppActionDefinition extends EditDetailAppActionDefinition {
    public EditDetailPageAppActionDefinition(final String appName, final String subAppName) {
        super("editPage", appName, subAppName, MagnoliaIcons.EDIT.getCssClass(), null);
    }
}

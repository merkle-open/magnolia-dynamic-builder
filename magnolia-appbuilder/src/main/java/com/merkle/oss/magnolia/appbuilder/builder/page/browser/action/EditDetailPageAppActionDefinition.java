package com.merkle.oss.magnolia.appbuilder.builder.page.browser.action;

import com.merkle.oss.magnolia.appbuilder.action.edit.EditDetailAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.PageDetailAppBuilder;
import info.magnolia.icons.MagnoliaIcons;

public class EditDetailPageAppActionDefinition extends EditDetailAppActionDefinition {

    public EditDetailPageAppActionDefinition(final String appName) {
        super("editPage", appName, PageDetailAppBuilder.NAME, MagnoliaIcons.EDIT.getCssClass(), null);
    }
}

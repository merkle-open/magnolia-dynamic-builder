package com.merkle.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.contentapp.action.OpenDetailSubappActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.contentapp.detail.ContentDetailSubApp;

import javax.annotation.Nullable;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

public class EditDetailAppActionDefinition implements AppActionDefinition {
    private final String name;
    private final String appName;
    private final String subAppName;
    private final String icon;
    @Nullable
    private final String label;

    public EditDetailAppActionDefinition(final String name, final String appName, final String subAppName) {
        this(name, appName, subAppName, MagnoliaIcons.EDIT.getCssClass(), null);
    }

    public EditDetailAppActionDefinition(final String name, final String appName, final String subAppName, final String icon, @Nullable final String label) {
        this.name = name;
        this.appName = appName;
        this.subAppName = subAppName;
        this.icon = icon;
        this.label = label;
    }

    @Override
	public OpenDetailSubappActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final OpenDetailSubappActionDefinition definition = new OpenDetailSubappActionDefinition();
        definition.setName(name);
        definition.setLabel(label);
        definition.setIcon(icon);
        definition.setAppName(appName);
        definition.setSubAppName(subAppName);
        definition.setViewType(ContentDetailSubApp.VIEW_TYPE_EDIT);
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new JcrIsNotDeletedRuleDefinition())
                .rule(new PermissionRequiredRuleDefinition(Permission.SET | Permission.READ))
                .build()
        );
        return definition;
	}
}

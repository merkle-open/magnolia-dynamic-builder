package com.merkle.oss.magnolia.appbuilder.action.add;

import info.magnolia.cms.security.Permission;
import info.magnolia.ui.contentapp.action.OpenDetailSubappActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.contentapp.detail.ContentDetailSubApp;

import javax.annotation.Nullable;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.OpenDetailSubappAction;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

public class AddDetailAppActionDefinition implements AppActionDefinition {
    private final String name;
    private final String appName;
    private final String subAppName;
    private final String nodeType;
    private final String icon;
    private final Class<? extends NodeNameProvider> nodeNameProviderClass;
    @Nullable
    private final String label;

    public AddDetailAppActionDefinition(
            final String name,
            final String appName,
            final String subAppName,
            final String nodeType,
            final String icon
    ) {
        this(name, appName, subAppName, nodeType, icon, null);
    }

    public AddDetailAppActionDefinition(
            final String name,
            final String appName,
            final String subAppName,
            final String nodeType,
            final String icon,
            @Nullable final String label
    ) {
        this(name, appName, subAppName, nodeType, icon, JcrNameNodeNameProvider.class, label);
    }
    public AddDetailAppActionDefinition(
            final String name,
            final String appName,
            final String subAppName,
            final String nodeType,
            final String icon,
            final Class<? extends NodeNameProvider> nodeNameProviderClass,
            @Nullable final String label
    ) {
        this.name = name;
        this.appName = appName;
        this.subAppName = subAppName;
        this.nodeType = nodeType;
        this.icon = icon;
        this.nodeNameProviderClass = nodeNameProviderClass;
        this.label = label;
    }

	@Override
	public OpenDetailSubappActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final OpenDetailSubappAction.Definition definition = new OpenDetailSubappAction.Definition(appName, subAppName);
        definition.setNodeType(nodeType);
        definition.setNodeNameProviderClass(nodeNameProviderClass);

        definition.setName(name);
        definition.setLabel(label);
        definition.setIcon(icon);
        definition.setViewType(ContentDetailSubApp.VIEW_TYPE_ADD);
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new JcrIsNotDeletedRuleDefinition())
                .rule(new PermissionRequiredRuleDefinition(Permission.ADD))
                .build()
        );
        return definition;
	}
}

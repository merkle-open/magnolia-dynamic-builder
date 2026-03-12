package com.merkle.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.AccessManager;
import info.magnolia.cms.security.Permission;
import info.magnolia.context.Context;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.contentapp.action.OpenDetailSubappAction;
import info.magnolia.ui.contentapp.action.OpenDetailSubappActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.contentapp.detail.ContentDetailSubApp;
import info.magnolia.ui.datasource.ItemResolver;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class EditDetailAppActionDefinition implements AppActionDefinition {
    private final String name;
    private final String appName;
    private final String subAppName;
    private final String icon;
    private final String viewType;
    @Nullable
    private final String label;

    public EditDetailAppActionDefinition(final String name, final String appName, final String subAppName) {
        this(name, appName, subAppName, MagnoliaIcons.EDIT.getCssClass(), ContentDetailSubApp.VIEW_TYPE_EDIT, null);
    }
    public EditDetailAppActionDefinition(final String name, final String appName, final String subAppName, final String viewType) {
        this(name, appName, subAppName, MagnoliaIcons.EDIT.getCssClass(), viewType, null);
    }
    public EditDetailAppActionDefinition(final String name, final String appName, final String subAppName, final String icon, final String viewType, @Nullable final String label) {
        this.name = name;
        this.appName = appName;
        this.subAppName = subAppName;
        this.icon = icon;
        this.label = label;
        this.viewType = viewType;
    }

    @Override
	public OpenDetailSubappActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final OpenDetailSubappActionDefinition definition = new OpenDetailSubappActionDefinition();
        definition.setImplementationClass(PermissionBasedOpenDetailSubappAction.class);
        definition.setName(name);
        definition.setLabel(label);
        definition.setIcon(icon);
        definition.setAppName(appName);
        definition.setSubAppName(subAppName);
        definition.setViewType(viewType);
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new JcrIsNotDeletedRuleDefinition())
                .rule(new PermissionRequiredRuleDefinition(Permission.READ))
                .build()
        );
        return definition;
	}

    public static class PermissionBasedOpenDetailSubappAction<T> extends OpenDetailSubappAction<T> {
        private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
        private final Provider<Context> contextProvider;

        @Inject
        public PermissionBasedOpenDetailSubappAction(
                final OpenDetailSubappActionDefinition definition,
                final ValueContext<T> valueContext,
                final LocationController locationController,
                final ItemResolver<T> itemResolver,
                final Provider<Context> contextProvider
        ) {
            super(definition, valueContext, locationController, itemResolver);
            this.contextProvider = contextProvider;
        }

        @Override
        protected ContentDetailSubApp.DetailLocation getLocation(final Optional<T> item, final ItemResolver<T> itemResolver) {
            final ContentDetailSubApp.DetailLocation location = super.getLocation(item, itemResolver);
            location.updateViewtype(resolveViewType(getDefinition().getViewType(), item.orElse(null)));
            return location;
        }

        private String resolveViewType(final String viewType, @Nullable final T item) {
            if(ContentDetailSubApp.VIEW_TYPE_EDIT.equals(viewType) && !hasEditPermissions(item)) {
                return ContentDetailSubApp.VIEW_TYPE_VIEW;
            }
            return viewType;
        }

        private boolean hasEditPermissions(@Nullable final T item) {
            if (item instanceof Node node) {
                try {
                    final AccessManager ami = contextProvider.get().getAccessManager(node.getSession().getWorkspace().getName());
                    return ami.isGranted(node.getPath(), Permission.SET);
                } catch (final Exception e) {
                    LOG.error("Failed to evaluate if user has permissions for {}! returning false", NodeUtil.getPathIfPossible(node), e);
                }
            }
            return false;
        }
    }
}

package com.merkle.oss.magnolia.dialogbuilder.parameter;

import info.magnolia.cms.security.MgnlUser;
import info.magnolia.cms.security.User;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.context.WebContext;
import info.magnolia.ui.dialog.ConfiguredFormDialogDefinition;

import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;

/**
 * Resolves the following types:
 * <ul>
 * <li>{@link DialogCreationContext}
 * <li>{@link Node}
 * <li>{@link WebContext}
 * <li>{@link Context}
 * <li>{@link User}
 * <li>{@link MgnlUser}
 * <li>{@link ConfiguredFormDialogDefinition} - doesn't contain layout and properties in tabFactory methods
 * </ul>
 */
public class DefaultDialogParameterResolver extends ParameterResolver {
    private final ConfiguredFormDialogDefinition<Node> dialog;
    private final DialogCreationContext context;

    public DefaultDialogParameterResolver(
            final ConfiguredFormDialogDefinition<Node> dialog,
            final DialogCreationContext context
    ) {
        super(null);
        this.dialog = dialog;
        this.context = context;
    }

    @Override
    public Object resolveParameter(final Class<?> parameterType) {
        if (parameterType.equals(ConfiguredFormDialogDefinition.class)) {
            return dialog;
        }
        if (parameterType.equals(DialogCreationContext.class)) {
            return context;
        }
        if (parameterType.equals(Node.class)) {
            return context.contentNode();
        }
        if (parameterType.isAssignableFrom(WebContext.class)) {
            return MgnlContext.getWebContext();
        }
        if (parameterType.isAssignableFrom(Context.class)) {
            return MgnlContext.getInstance();
        }
        if (parameterType.isAssignableFrom(User.class)) {
            return MgnlContext.getUser();
        }
        if (parameterType.isAssignableFrom(MgnlUser.class)) {
            return MgnlContext.getUser();
        }
        return super.resolveParameter(parameterType);
    }

    public static class Factory implements DialogParameterResolverFactory {
        @Override
        public ParameterResolver create(final DialogCreationContext context, final ConfiguredFormDialogDefinition<Node> dialog) {
            return new DefaultDialogParameterResolver(dialog, context);
        }
    }
}

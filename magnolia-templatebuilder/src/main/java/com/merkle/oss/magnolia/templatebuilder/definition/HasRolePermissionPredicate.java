package com.merkle.oss.magnolia.templatebuilder.definition;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.template.TemplateDefinition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import jakarta.inject.Inject;
import javax.jcr.Node;

public class HasRolePermissionPredicate implements PermissionPredicate {
    private final Map<String, String> params;

    @Inject
    public HasRolePermissionPredicate(final Map<String, String> params) {
        this.params = params;
    }

    @Override
    public boolean test(final Node node, final TemplateDefinition definition) {
        final String roles = Optional.ofNullable(params.get("roles")).orElseThrow(() ->
                new NullPointerException("no roles specified! invalid permission config for "+definition.getId())
        );
        return Arrays.stream(roles.split(",")).anyMatch(getRoles()::contains);
    }

    protected Collection<String> getRoles() {
        return MgnlContext.getUser().getAllRoles();
    }
}

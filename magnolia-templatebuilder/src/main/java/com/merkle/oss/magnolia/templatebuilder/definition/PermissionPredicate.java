package com.merkle.oss.magnolia.templatebuilder.definition;

import info.magnolia.rendering.template.TemplateDefinition;

import javax.jcr.Node;

public interface PermissionPredicate {
    boolean test(Node node, TemplateDefinition definition);

    abstract class Unspecified implements PermissionPredicate {}

    class Allowed implements PermissionPredicate {
        @Override
        public boolean test(final Node node, final TemplateDefinition definition) {
            return true;
        }
    }
    class Forbidden implements PermissionPredicate {
        @Override
        public boolean test(final Node node, final TemplateDefinition definition) {
            return false;
        }
    }
}

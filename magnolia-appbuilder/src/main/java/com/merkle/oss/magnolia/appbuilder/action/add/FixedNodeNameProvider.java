package com.merkle.oss.magnolia.appbuilder.action.add;

import info.magnolia.jcr.util.NodeNameHelper;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.editor.FormView;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public abstract class FixedNodeNameProvider implements NodeNameProvider {
    private final NodeNameHelper nodeNameHelper;
    private final String name;

    protected FixedNodeNameProvider(final NodeNameHelper nodeNameHelper, final String name) {
        this.nodeNameHelper = nodeNameHelper;
        this.name = name;
    }

    @Override
    public String get(final FormView<Node> formView, final Node parent) throws ActionExecutionException {
        try {
            return nodeNameHelper.getUniqueName(parent, name);
        } catch (RepositoryException e) {
            throw new ActionExecutionException("Failed to provide node name", e);
        }
    }
}

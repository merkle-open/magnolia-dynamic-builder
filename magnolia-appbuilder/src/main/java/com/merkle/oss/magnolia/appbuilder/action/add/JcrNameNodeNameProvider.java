package com.merkle.oss.magnolia.appbuilder.action.add;

import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.editor.FormView;

import javax.jcr.Node;

public class JcrNameNodeNameProvider implements NodeNameProvider {
    @Override
    public String get(final FormView<Node> formView, final Node parent) throws ActionExecutionException {
        return formView.getPropertyValue("jcrName").map(String::valueOf).orElseThrow(() ->
                new ActionExecutionException("Failed to get 'jcrName' property from form - Make sure your dialog has a field named 'jcrName'!")
        );
    }
}

package com.merkle.oss.magnolia.appbuilder.action.add;

import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.editor.FormView;

import javax.jcr.Node;

public interface NodeNameProvider {
    String get(FormView<Node> formView, Node parent) throws ActionExecutionException;
}

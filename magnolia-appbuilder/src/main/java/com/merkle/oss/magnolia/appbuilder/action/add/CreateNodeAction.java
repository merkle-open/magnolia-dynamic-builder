package com.merkle.oss.magnolia.appbuilder.action.add;

import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.CloseHandler;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.contentapp.Datasource;
import info.magnolia.ui.contentapp.action.CommitAction;
import info.magnolia.ui.datasource.jcr.JcrDatasource;
import info.magnolia.ui.editor.FormView;
import info.magnolia.ui.observation.DatasourceObservation;

import javax.inject.Inject;
import javax.jcr.Node;

import com.machinezoo.noexception.Exceptions;

public class CreateNodeAction extends CommitAction<Node> {
	private final CreateNodeActionDefinition definition;
	private final JcrDatasource jcrDatasource;
	private final CreateNodeActionDefinition.NodeNameProvider nodeNameProvider;

	@Inject
	public CreateNodeAction(
			final CreateNodeActionDefinition definition,
			final FormView<Node> form,
			final ValueContext<Node> valueContext,
			final JcrDatasource jcrDatasource,
			final CloseHandler closeHandler,
			final DatasourceObservation.Manual datasourceObservation,
			final ComponentProvider componentProvider
	) {
		super(definition, closeHandler, valueContext, form, (Datasource) jcrDatasource, datasourceObservation);
		this.definition = definition;
		this.jcrDatasource = jcrDatasource;
		this.nodeNameProvider = componentProvider.newInstance(definition.getNodeNameProviderClass());
	}

	@Override
	public void execute() {
		if (validateForm()) {
			Exceptions.wrap().run(() -> {
				final Node parent = getValueContext().getSingle().orElseGet(jcrDatasource::getRoot);
				final String nodeName = nodeNameProvider.get(getForm(), parent);
				final Node node = NodeUtil.createPath(parent, nodeName, definition.getNodeType());
				getValueContext().set(node);
				super.execute();
			});
		}
	}

	@Override
	protected FormView<Node> getForm() {
		return (FormView<Node>) super.getForm();
	}
}

package com.namics.oss.magnolia.appbuilder.action.add;

import com.machinezoo.noexception.Exceptions;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.ui.CloseHandler;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.contentapp.Datasource;
import info.magnolia.ui.contentapp.action.CommitAction;
import info.magnolia.ui.datasource.jcr.JcrDatasource;
import info.magnolia.ui.editor.FormView;
import info.magnolia.ui.observation.DatasourceObservation;

import javax.inject.Inject;
import javax.jcr.Node;

public class CreateNodeAction extends CommitAction<Node> {
	private final CreateNodeActionDefinition definition;
	private final JcrDatasource jcrDatasource;

	@Inject
	public CreateNodeAction(
			final CreateNodeActionDefinition definition,
			final FormView<Node> form,
			final ValueContext<Node> valueContext,
			final JcrDatasource jcrDatasource,
			final CloseHandler closeHandler,
			final DatasourceObservation.Manual datasourceObservation
	) {
		super(definition, closeHandler, valueContext, form, (Datasource) jcrDatasource, datasourceObservation);
		this.definition = definition;
		this.jcrDatasource = jcrDatasource;
	}

	@Override
	public void execute() {
		if (validateForm()) {
			Exceptions.wrap().run(() -> {
				final Node parent = getValueContext().getSingle().orElseGet(jcrDatasource::getRoot);
				final String nodeName = getForm().getPropertyValue("jcrName").map(String::valueOf).orElseThrow(() ->
						new ActionExecutionException("Failed to get 'jcrName' property from form - Make sure your dialog has a field named 'jcrName'!")
				);

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

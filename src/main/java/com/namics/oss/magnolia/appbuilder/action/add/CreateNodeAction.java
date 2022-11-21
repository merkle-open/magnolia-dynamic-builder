package com.namics.oss.magnolia.appbuilder.action.add;

import com.machinezoo.noexception.Exceptions;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.HasValue;
import info.magnolia.jcr.util.NodeNameHelper;
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
import java.util.Optional;

public class CreateNodeAction extends CommitAction<Node> {
	private final CreateNodeActionDefinition definition;
	private final JcrDatasource jcrDatasource;
	private final NodeNameHelper nodeNameHelper;

	@Inject
	CreateNodeAction(
			final CreateNodeActionDefinition definition,
			final FormView<Node> form,
			final ValueContext<Node> valueContext,
			final JcrDatasource jcrDatasource,
			final CloseHandler closeHandler,
			final NodeNameHelper nodeNameHelper,
			final DatasourceObservation.Manual datasourceObservation) {
		super(definition, closeHandler, valueContext, form, (Datasource) jcrDatasource, datasourceObservation);
		this.definition = definition;
		this.jcrDatasource = jcrDatasource;
		this.nodeNameHelper = nodeNameHelper;
	}

	@Override
	public void execute() {
		if (validateForm()) {
			Exceptions.wrap().run(() -> {
				final Node parent = getValueContext().getSingle().orElseGet(jcrDatasource::getRoot);
				final String nodeName = nodeNameHelper.getUniqueName(
						parent,
						getForm().getPropertyValue(definition.getNodeNameProperty()).map(String::valueOf).orElseThrow(() ->
								new ActionExecutionException("Failed to get node name property " + definition.getNodeNameProperty() + "from form")
						)
				);
				getNodeNameField().ifPresent(field -> field.setValue(nodeName));

				final Node node = NodeUtil.createPath(parent, nodeName, definition.getNodeType());
				getValueContext().set(node);
				super.execute();
			});
		}
	}

	private Optional<HasValue<String>> getNodeNameField() {
		return getForm().validate().stream()
				.map(BinderValidationStatus::getBinder)
				.map(binder -> binder.getBinding(definition.getNodeNameProperty()))
				.flatMap(Optional::stream)
				.map(binding -> (HasValue<String>) binding.getField())
				.findFirst();
	}

	@Override
	protected FormView<Node> getForm() {
		return (FormView<Node>) super.getForm();
	}
}

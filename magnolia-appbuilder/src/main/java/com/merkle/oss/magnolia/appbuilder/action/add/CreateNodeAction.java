package com.merkle.oss.magnolia.appbuilder.action.add;

import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.CloseHandler;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.contentapp.Datasource;
import info.magnolia.ui.contentapp.action.CommitAction;
import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import info.magnolia.ui.editor.FormView;
import info.magnolia.ui.observation.DatasourceObservation;

import java.io.Serializable;

import javax.jcr.Node;

import com.machinezoo.noexception.Exceptions;

import jakarta.inject.Inject;

public class CreateNodeAction extends CommitAction<Node> {
	private final Definition definition;
    private final NodeNameProvider nodeNameProvider;

	@Inject
	public CreateNodeAction(
			final Definition definition,
			final FormView<Node> form,
			final ValueContext<Node> valueContext,
			final Datasource<Node> datasource,
			final CloseHandler closeHandler,
			final DatasourceObservation.Manual datasourceObservation,
			final ComponentProvider componentProvider
	) {
		super(definition, closeHandler, valueContext, form, datasource, datasourceObservation);
		this.definition = definition;
        this.nodeNameProvider = componentProvider.newInstance(definition.getNodeNameProviderClass());
	}

	@Override
	protected void write() {
		Exceptions.wrap().run(() -> {
			final Node parent = getValueContext().getSingle().orElseGet(getDatasource()::getRoot);
			final String nodeName = nodeNameProvider.get(getForm(), parent);
			final Node node = NodeUtil.createPath(parent, nodeName, definition.getNodeType());
			getForm().write(node);
			getDatasource().save(node);
			getValueContext().set(node);
			if (ActionDefinition.RefreshBehavior.ITEMS.equals(getDefinition().getDatasourceRefreshBehavior())) {
				getDatasourceObservation().trigger(node);
			} else {
				getDatasourceObservation().trigger();
			}
		});
	}

	@Override
	protected FormView<Node> getForm() {
		return (FormView<Node>) super.getForm();
	}

	public static class Definition extends CommitActionDefinition implements Serializable {
		private final String nodeType;
		private final Class<? extends NodeNameProvider> nodeNameProviderClass;

		public Definition(
				final String nodeType,
				final Class<? extends NodeNameProvider> nodeNameProviderClass
		) {
			this.nodeType = nodeType;
			this.nodeNameProviderClass = nodeNameProviderClass;
			setImplementationClass(CreateNodeAction.class);
		}

		public String getNodeType() {
			return nodeType;
		}

		public Class<? extends NodeNameProvider> getNodeNameProviderClass() {
			return nodeNameProviderClass;
		}
	}
}

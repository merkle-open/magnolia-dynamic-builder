package com.namics.oss.magnolia.appbuilder;

import com.google.common.collect.ImmutableList;
import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.builder.ModificationDateColumnDefinition;
import com.vaadin.data.ValueProvider;
import com.vaadin.v7.ui.Table;
import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.api.app.AppDescriptor;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.contentapp.ConfiguredContentAppDescriptor;
import info.magnolia.ui.contentapp.ContentApp;
import info.magnolia.ui.contentapp.browser.BrowserSubAppDescriptor;
import info.magnolia.ui.contentapp.browser.ConfiguredBrowserSubAppDescriptor;
import info.magnolia.ui.contentapp.column.jcr.JcrStatusColumnDefinition;
import info.magnolia.ui.contentapp.configuration.*;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredJcrContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredNodeTypeDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.JcrContentConnectorDefinition;
import info.magnolia.ui.workbench.column.AbstractColumnFormatter;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;
import info.magnolia.ui.workbench.definition.ConfiguredWorkbenchDefinition;
import info.magnolia.ui.workbench.definition.ContentPresenterDefinition;
import info.magnolia.ui.workbench.list.ListPresenterDefinition;
import info.magnolia.ui.workbench.search.SearchPresenterDefinition;
import info.magnolia.ui.workbench.tree.TreePresenterDefinition;
import org.apache.jackrabbit.JcrConstants;
import org.springframework.aop.support.AopUtils;

import javax.inject.Inject;
import javax.jcr.Item;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Provides legacy/UI5 appDescriptor to be able to open a legacy/UI5 chooser dialog to content from an UI6 app.
 * <br>
 * See <a href="https://docs.magnolia-cms.com/product-docs/6.2/Support/Magnolia-5-UI-documentation/Dialog-definition-5-UI/Field-definition-5-UI/List-of-fields-5-UI/Link-field-5-UI.html">appName description warning</a>
 */
public class LegacyAppDescriptorProvider extends AppDescriptorProvider {
	public static final UnaryOperator<String> CONVERT_APP_NAME = appName -> appName + "-legacyChooser";
	private final DefinitionMetadata metadata;
	private final boolean shouldRegister;

	public LegacyAppDescriptorProvider(final Object appFactory) {
		super(appFactory);
		final AppFactory appFactoryAnnotation = AopUtils
				.getTargetClass(appFactory)
				.getAnnotation(AppFactory.class);
		this.shouldRegister = appFactoryAnnotation.generateLegacyChooserApp();
		this.metadata = new LegacyAppDefinitionMetaDataBuilder(appFactory).build();
	}

	boolean shouldRegister() {
		return shouldRegister;
	}

	@Override
	public AppDescriptor get() throws Registry.InvalidDefinitionException {
		final AppDescriptor appDescriptor = super.get();
		final ConfiguredContentAppDescriptor legacyAppDescriptor = new ConfiguredContentAppDescriptor();
		legacyAppDescriptor.setName(CONVERT_APP_NAME.apply(appDescriptor.getName()));
		legacyAppDescriptor.setAppClass(ContentApp.class);
		legacyAppDescriptor.setEnabled(false);
		legacyAppDescriptor.setSubApps(getBrowserSubApps(appDescriptor));
		return legacyAppDescriptor;
	}

	@Override
	public DefinitionMetadata getMetadata() {
		return metadata;
	}

	private Map<String, SubAppDescriptor> getBrowserSubApps(final AppDescriptor appDescriptor) {
		return appDescriptor.getSubApps().values().stream()
				.filter(BrowserDescriptor.class::isInstance)
				.map(browserDescriptor -> (BrowserDescriptor<?,?>) browserDescriptor)
				.map(browserDescriptor -> convert(appDescriptor, browserDescriptor))
				.collect(Collectors.toMap(SubAppDescriptor::getName, Function.identity()));
	}

	private <T> BrowserSubAppDescriptor convert(final AppDescriptor appDescriptor, final BrowserDescriptor<T, ?> browserDescriptor) {
		final ConfiguredBrowserSubAppDescriptor legacyBrowserDescriptor = new ConfiguredBrowserSubAppDescriptor();
		legacyBrowserDescriptor.setName(browserDescriptor.getName());
		legacyBrowserDescriptor.setIcon(browserDescriptor.getIcon());
		legacyBrowserDescriptor.setLabel(browserDescriptor.getLabel());
		legacyBrowserDescriptor.setContentConnector(convert((JcrDatasourceDefinition) browserDescriptor.getDatasource()));
		legacyBrowserDescriptor.setWorkbench(convert(appDescriptor, browserDescriptor, browserDescriptor.getWorkbench()));
		return legacyBrowserDescriptor;
	}

	private JcrContentConnectorDefinition convert(final JcrDatasourceDefinition jcrDatasourceDefinition) {
		final ConfiguredJcrContentConnectorDefinition legacyJcrContentConnectorDefinition = new ConfiguredJcrContentConnectorDefinition();
		legacyJcrContentConnectorDefinition.setWorkspace(jcrDatasourceDefinition.getWorkspace());
		legacyJcrContentConnectorDefinition.setDefaultOrder(jcrDatasourceDefinition.getSortByProperties().stream().findFirst().orElse(JcrConstants.JCR_NAME));
		legacyJcrContentConnectorDefinition.setNodeTypes(
				jcrDatasourceDefinition.getAllowedNodeTypes().stream().map(nodeType -> {
					final ConfiguredNodeTypeDefinition nodeTypeDefinition = new ConfiguredNodeTypeDefinition();
					nodeTypeDefinition.setName(nodeType);
					return nodeTypeDefinition;
				}).collect(Collectors.toList())
		);
		return legacyJcrContentConnectorDefinition;
	}

	private <T> info.magnolia.ui.workbench.definition.WorkbenchDefinition convert(
			final AppDescriptor appDescriptor,
			final BrowserDescriptor<T, ?> browserDescriptor,
			final WorkbenchDefinition<T> workbench
	) {
		final ConfiguredWorkbenchDefinition legacyWorkbench = new ConfiguredWorkbenchDefinition();
		legacyWorkbench.setName(workbench.getName());
		legacyWorkbench.setDialogWorkbench(true);
		legacyWorkbench.setContentViews(getContentViews(appDescriptor, browserDescriptor, workbench));
		return legacyWorkbench;
	}

	protected <T> List<ContentPresenterDefinition> getContentViews(
			final AppDescriptor appDescriptor,
			final BrowserDescriptor<T, ?> browserDescriptor,
			final WorkbenchDefinition<T> workbench
	) {
		final ImmutableList.Builder<ContentPresenterDefinition> presenters = ImmutableList.builder();
		workbench.getContentViews().stream()
				.filter(TreeViewDefinition.class::isInstance)
				.map(v -> (TreeViewDefinition<T>)v)
				.findFirst()
				.map(treeView -> getColumns(appDescriptor, browserDescriptor, treeView))
				.ifPresent(columns -> {
					final TreePresenterDefinition treePresenter = new TreePresenterDefinition();
					treePresenter.setColumns(columns);
					presenters.add(treePresenter);
				});
		workbench.getContentViews().stream()
				.filter(ListViewDefinition.class::isInstance)
				.map(v -> (ListViewDefinition<T>)v)
				.findFirst()
				.map(treeView -> getColumns(appDescriptor, browserDescriptor, treeView))
				.ifPresent(columns -> {
					final ListPresenterDefinition listPresenter = new ListPresenterDefinition();
					listPresenter.setColumns(columns);
					presenters.add(listPresenter);

					final SearchPresenterDefinition searchPresenter = new SearchPresenterDefinition();
					searchPresenter.setColumns(columns);
					presenters.add(searchPresenter);
				});
		return presenters.build();
	}

	private <T> List<info.magnolia.ui.workbench.column.definition.ColumnDefinition> getColumns(
			final AppDescriptor appDescriptor,
			final BrowserDescriptor<T, ?> browserDescriptor,
			final GridViewDefinition<T> gridViewDefinition
	) {
		return gridViewDefinition.getColumns()
				.stream()
				.filter(Predicate.not(ModificationDateColumnDefinition.class::isInstance).and(Predicate.not(JcrStatusColumnDefinition.class::isInstance)))
				.map(column ->
						convert(appDescriptor, browserDescriptor, column)
				)
				.collect(Collectors.toList());
	}

	private <T> LegacyPropertyColumnDefinition<T> convert(
			final AppDescriptor appDescriptor,
			final BrowserDescriptor<T, ?> browserDescriptor,
			final ColumnDefinition<T> column
	) {
		final LegacyPropertyColumnDefinition<T> legacyColumn = new LegacyPropertyColumnDefinition<>();
		legacyColumn.setName(column.getName());
		legacyColumn.setWidth((int) column.getWidth());
		legacyColumn.setExpandRatio(column.getExpandRatio());
		legacyColumn.setLabel(appDescriptor.getName() + "." + browserDescriptor.getName() + ".views." + column.getName() + ".label");
		legacyColumn.setSortable(column.isSortable());
		legacyColumn.setPropertyName(column.getName());
		legacyColumn.setColumn(column);
		Optional.ofNullable(column.getValueProvider()).ifPresent(valueProvider ->
			legacyColumn.setFormatterClass(ValueProviderWrappingColumnFormatter.class)
		);
		return legacyColumn;
	}

	public static class ValueProviderWrappingColumnFormatter<T> extends AbstractColumnFormatter<LegacyPropertyColumnDefinition<T>> {
		private final ValueProvider<Item, ?> valueProvider;

		@Inject
		public ValueProviderWrappingColumnFormatter(
				final ComponentProvider componentProvider,
				final LegacyPropertyColumnDefinition<T> definition
		) {
			super(definition);
			this.valueProvider = (ValueProvider<Item, ?>)componentProvider.newInstance(definition.getColumn().getValueProvider(), definition.getColumn());
		}

		@Override
		public Object generateCell(final Table source, final Object itemId, final Object columnId) {
			return valueProvider.apply(getJcrItem(source, itemId));
		}
	}

	public static class LegacyPropertyColumnDefinition<T> extends PropertyColumnDefinition {
		private ColumnDefinition<T> column;

		public ColumnDefinition<T> getColumn() {
			return column;
		}
		public void setColumn(final ColumnDefinition<T> column) {
			this.column = column;
		}
	}

	private static class LegacyAppDefinitionMetaDataBuilder extends AppDefinitionMetaDataBuilder {
		public LegacyAppDefinitionMetaDataBuilder(final Object appFactory) {
			super(appFactory);
		}

		@Override
		protected String generateName(final AppFactory appFactoryAnnotation) {
			return CONVERT_APP_NAME.apply(super.generateName(appFactoryAnnotation));
		}
		@Override
		protected String generateLocation(final Object appFactory) {
			return CONVERT_APP_NAME.apply(super.generateLocation(appFactory));
		}
	}
}

package com.namics.oss.magnolia.appbuilder;

import com.google.common.collect.ImmutableList;
import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.ui.api.app.AppDescriptor;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.contentapp.ConfiguredContentAppDescriptor;
import info.magnolia.ui.contentapp.ContentApp;
import info.magnolia.ui.contentapp.browser.BrowserSubAppDescriptor;
import info.magnolia.ui.contentapp.browser.ConfiguredBrowserSubAppDescriptor;
import info.magnolia.ui.contentapp.column.jcr.JcrTitleColumnDefinition;
import info.magnolia.ui.contentapp.configuration.BrowserDescriptor;
import info.magnolia.ui.contentapp.configuration.ContentViewDefinition;
import info.magnolia.ui.contentapp.configuration.TreeViewDefinition;
import info.magnolia.ui.contentapp.configuration.WorkbenchDefinition;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredJcrContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredNodeTypeDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.JcrContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.jcr.ModelConstants;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;
import info.magnolia.ui.workbench.definition.ConfiguredWorkbenchDefinition;
import info.magnolia.ui.workbench.tree.TreePresenterDefinition;
import org.springframework.aop.support.AopUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
				.map(BrowserDescriptor.class::cast)
				.map(browserDescriptor -> convert(appDescriptor, browserDescriptor))
				.collect(Collectors.toMap(SubAppDescriptor::getName, Function.identity()));
	}

	private BrowserSubAppDescriptor convert(final AppDescriptor appDescriptor, final BrowserDescriptor browserDescriptor) {
		final ConfiguredBrowserSubAppDescriptor legacyBrowserDescriptor = new ConfiguredBrowserSubAppDescriptor();
		legacyBrowserDescriptor.setName(browserDescriptor.getName());
		legacyBrowserDescriptor.setIcon(browserDescriptor.getIcon());
		legacyBrowserDescriptor.setLabel(browserDescriptor.getLabel());
		legacyBrowserDescriptor.setContentConnector(convert(
				(JcrDatasourceDefinition) browserDescriptor.getDatasource(),
				getIcons(browserDescriptor.getWorkbench())
		));
		legacyBrowserDescriptor.setWorkbench(convert(appDescriptor, browserDescriptor, browserDescriptor.getWorkbench()));
		return legacyBrowserDescriptor;
	}

	private JcrContentConnectorDefinition convert(final JcrDatasourceDefinition jcrDatasourceDefinition, final Map<String, String> icons) {
		final ConfiguredJcrContentConnectorDefinition legacyJcrContentConnectorDefinition = new ConfiguredJcrContentConnectorDefinition();
		legacyJcrContentConnectorDefinition.setWorkspace(jcrDatasourceDefinition.getWorkspace());
		legacyJcrContentConnectorDefinition.setNodeTypes(
				jcrDatasourceDefinition.getAllowedNodeTypes().stream().map(nodeType -> {
					final ConfiguredNodeTypeDefinition nodeTypeDefinition = new ConfiguredNodeTypeDefinition();
					nodeTypeDefinition.setName(nodeType);
					nodeTypeDefinition.setIcon(icons.get(nodeType));
					return nodeTypeDefinition;
				}).collect(Collectors.toList())
		);
		return legacyJcrContentConnectorDefinition;
	}

	private Map<String, String> getIcons(final WorkbenchDefinition workbench) {
		return streamTreeViewColumns(workbench)
				.filter(JcrTitleColumnDefinition.class::isInstance)
				.map(JcrTitleColumnDefinition.class::cast)
				.map(JcrTitleColumnDefinition::getNodeTypeToIcon)
				.findFirst()
				.orElseGet(Collections::emptyMap);
	}

	private Stream<ColumnDefinition> streamTreeViewColumns(final WorkbenchDefinition workbench) {
		return ((List<ContentViewDefinition>) workbench.getContentViews())
				.stream()
				.filter(TreeViewDefinition.class::isInstance)
				.map(TreeViewDefinition.class::cast)
				.map(view -> (List<ColumnDefinition>) view.getColumns())
				.flatMap(Collection::stream);
	}

	private info.magnolia.ui.workbench.definition.WorkbenchDefinition convert(
			final AppDescriptor appDescriptor,
			final BrowserDescriptor browserDescriptor,
			final WorkbenchDefinition workbench
	) {
		final ConfiguredWorkbenchDefinition legacyWorkbench = new ConfiguredWorkbenchDefinition();
		legacyWorkbench.setName(workbench.getName());
		legacyWorkbench.setDialogWorkbench(true);
		legacyWorkbench.setContentViews(List.of(getContentView(appDescriptor, browserDescriptor, workbench)));
		return legacyWorkbench;
	}

	protected TreePresenterDefinition getContentView(
			final AppDescriptor appDescriptor,
			final BrowserDescriptor browserDescriptor,
			final WorkbenchDefinition workbench
	) {
		final PropertyColumnDefinition nameColumn = new PropertyColumnDefinition();
		nameColumn.setName("name");
		nameColumn.setPropertyName(ModelConstants.JCR_NAME);

		final TreePresenterDefinition treePresenter = new TreePresenterDefinition();
		final List<info.magnolia.ui.workbench.column.definition.ColumnDefinition> simpleColumns = getSimpleColumns(appDescriptor, browserDescriptor, workbench);
		final ImmutableList.Builder<info.magnolia.ui.workbench.column.definition.ColumnDefinition> columns = ImmutableList.builder();
		if(simpleColumns.stream().noneMatch(column -> Objects.equals(column.getName(), nameColumn.getName()))) {
			//add name column on first place if missing
			columns.add(nameColumn);
		}
		columns.addAll(simpleColumns);
		treePresenter.setColumns(columns.build());
		return treePresenter;
	}

	private List<info.magnolia.ui.workbench.column.definition.ColumnDefinition> getSimpleColumns(
			final AppDescriptor appDescriptor,
			final BrowserDescriptor browserDescriptor,
			final WorkbenchDefinition workbench
	) {
		return streamTreeViewColumns(workbench)
				.filter(column -> column.getValueProvider() == null)
				.map(column ->
						convert(appDescriptor, browserDescriptor, column)
				)
				.collect(Collectors.toList());
	}

	private PropertyColumnDefinition convert(
			final AppDescriptor appDescriptor,
			final BrowserDescriptor browserDescriptor,
			final ColumnDefinition column
	) {
		final PropertyColumnDefinition legacyColumn = new PropertyColumnDefinition();
		legacyColumn.setName(column.getName());
		legacyColumn.setWidth((int) column.getWidth());
		legacyColumn.setExpandRatio(column.getExpandRatio());
		legacyColumn.setLabel(appDescriptor.getName() + "." + browserDescriptor.getName() + ".views." + column.getName() + ".label");
		legacyColumn.setSortable(column.isSortable());
		legacyColumn.setPropertyName(column.getName());
		return legacyColumn;
	}

	private static final class LegacyAppDefinitionMetaDataBuilder extends AppDefinitionMetaDataBuilder {
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

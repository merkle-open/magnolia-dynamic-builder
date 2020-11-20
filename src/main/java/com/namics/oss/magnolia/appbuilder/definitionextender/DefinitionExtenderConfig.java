package com.namics.oss.magnolia.appbuilder.definitionextender;

import com.namics.oss.magnolia.appbuilder.builder.helper.action.NodeTypeToActionDelegatingActionDefinition;
import info.magnolia.cms.security.operations.ConfiguredAccessDefinition;
import info.magnolia.cms.security.operations.ConfiguredOperationPermissionDefinition;
import info.magnolia.cms.security.operations.VoterBasedConfiguredAccessDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarGroupDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarItemDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarSectionDefinition;
import info.magnolia.ui.api.action.CommandActionDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.api.app.registry.ConfiguredSubAppDescriptor;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityRuleDefinition;
import info.magnolia.ui.contentapp.browser.ConfiguredBrowserSubAppDescriptor;
import info.magnolia.ui.contentapp.browser.action.*;
import info.magnolia.ui.contentapp.definition.ConfiguredContentSubAppDescriptor;
import info.magnolia.ui.contentapp.detail.ConfiguredDetailSubAppDescriptor;
import info.magnolia.ui.contentapp.field.LinkFieldSelectionDefinition;
import info.magnolia.ui.contentapp.field.WorkbenchFieldDefinition;
import info.magnolia.ui.contentapp.movedialog.action.OpenMoveDialogActionDefinition;
import info.magnolia.ui.dialog.definition.ConfiguredChooseDialogDefinition;
import info.magnolia.ui.form.field.definition.ConfiguredFieldDefinition;
import info.magnolia.ui.framework.action.*;
import info.magnolia.ui.framework.tools.ConfiguredToolsSubAppDescriptor;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredJcrContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredNodeTypeDefinition;
import info.magnolia.ui.workbench.column.definition.*;
import info.magnolia.ui.workbench.definition.ConfiguredContentPresenterDefinition;
import info.magnolia.ui.workbench.definition.ConfiguredWorkbenchDefinition;
import info.magnolia.ui.workbench.list.ListPresenterDefinition;
import info.magnolia.ui.workbench.search.SearchPresenterDefinition;
import info.magnolia.ui.workbench.tree.TreePresenterDefinition;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for {@link DefinitionExtender}.
 * <p>
 * The configured classes will be extended with
 * builder-style methods.
 * The list of classes should contain all definitions
 * needed to build a {@link info.magnolia.ui.contentapp.ContentApp}
 */
public class DefinitionExtenderConfig {

	// change this according to your project setup
	private static final String SOURCE_ROOT_PATH = "src/main/java";
	private static final String TARGET_PACKAGE_BASE = "com.namics.mgnl.appbuilder.builder.generated.";

	private List<Entry> config = new ArrayList<>();

	private DefinitionExtenderConfig() {
		//////////////////////////////////////////////
		// SubApp Definitions
		//////////////////////////////////////////////
		String subAppPackage = TARGET_PACKAGE_BASE + "subapp";
		config.add(new Entry()
				.classToExtend(ConfiguredSubAppDescriptor.class)
				.targetPackage(subAppPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredContentSubAppDescriptor.class)
				.targetPackage(subAppPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredBrowserSubAppDescriptor.class)
				.targetPackage(subAppPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredDetailSubAppDescriptor.class)
				.targetPackage(subAppPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredToolsSubAppDescriptor.class)
				.targetPackage(subAppPackage)
		);
		//////////////////////////////////////////////
		// Workbench Definitions
		//////////////////////////////////////////////
		config.add(new Entry()
				.classToExtend(ConfiguredWorkbenchDefinition.class)
				.targetPackage(TARGET_PACKAGE_BASE + "workbench")
		);
		//////////////////////////////////////////////
		// Content View Definitions
		//////////////////////////////////////////////
		String presenterPackage = TARGET_PACKAGE_BASE + "presenter";
		config.add(new Entry()
				.classToExtend(TreePresenterDefinition.class)
				.targetPackage(presenterPackage)
		);
		config.add(new Entry()
				.classToExtend(ListPresenterDefinition.class)
				.targetPackage(presenterPackage)
		);
		config.add(new Entry()
				.classToExtend(SearchPresenterDefinition.class)
				.targetPackage(presenterPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredContentPresenterDefinition.class)
				.targetPackage(presenterPackage)
		);
		//////////////////////////////////////////////
		// Content View Definitions
		//////////////////////////////////////////////
		String actionbarPackage = TARGET_PACKAGE_BASE + "actionbar";
		config.add(new Entry()
				.classToExtend(ConfiguredActionbarDefinition.class)
				.targetPackage(actionbarPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredActionbarGroupDefinition.class)
				.targetPackage(actionbarPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredActionbarItemDefinition.class)
				.targetPackage(actionbarPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredActionbarSectionDefinition.class)
				.targetPackage(actionbarPackage)
		);
		//////////////////////////////////////////////
		// Availability Definitions
		//////////////////////////////////////////////
		String availabilityPackage = TARGET_PACKAGE_BASE + "availability";
		config.add(new Entry()
				.classToExtend(ConfiguredAvailabilityDefinition.class)
				.targetPackage(availabilityPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredAvailabilityRuleDefinition.class)
				.targetPackage(availabilityPackage)
		);
		//////////////////////////////////////////////
		// Action Definitions
		//////////////////////////////////////////////
		String actionPackage = TARGET_PACKAGE_BASE + "action";
		String actionDeprecatedPackage = TARGET_PACKAGE_BASE + "action.deprecated";
		config.add(new Entry()
				.classToExtend(AddNodeActionDefinition.class)
				.targetPackage(actionDeprecatedPackage)
				.isDeprecated(true)
		);
		config.add(new Entry()
				.classToExtend(AddFolderActionDefinition.class)
				.targetPackage(actionDeprecatedPackage)
				.isDeprecated(true)
		);
		config.add(new Entry()
				.classToExtend(DuplicateNodeActionDefinition.class)
				.targetPackage(actionDeprecatedPackage)
				.isDeprecated(true)
		);
		config.add(new Entry()
				.classToExtend(ConfirmationActionDefinition.class)
				.targetPackage(actionDeprecatedPackage)
				.isDeprecated(true)
		);
		config.add(new Entry()
				.classToExtend(DeactivationActionDefinition.class)
				.targetPackage(actionDeprecatedPackage)
				.isDeprecated(true)
		);
		config.add(new Entry()
				.classToExtend(DeleteConfirmationActionDefinition.class)
				.targetPackage(actionDeprecatedPackage)
				.isDeprecated(true)
		);
		config.add(new Entry()
				.classToExtend(info.magnolia.ui.contentapp.action.AddNodeActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(info.magnolia.ui.contentapp.action.DeleteNodesConfirmationActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(info.magnolia.ui.contentapp.action.ConfirmationActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(info.magnolia.ui.contentapp.action.ChainedActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(info.magnolia.ui.contentapp.action.CloseActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(info.magnolia.ui.contentapp.action.CommitActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(info.magnolia.ui.contentapp.action.OpenDetailSubappActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(info.magnolia.ui.contentapp.action.SetDataFilterActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(CommandActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(ActivationActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(AddPropertyActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(DeleteActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(DeleteItemActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(DownloadBinaryActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(ExportYamlActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(MarkNodeAsDeletedActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(OpenCreateDialogActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(OpenEditDialogActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(OpenExportDialogActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(OpenLocationActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(OpenMoveDialogActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(SaveItemPropertyActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(ShowVersionsActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(RestoreVersionActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(PasteContentActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(ExpandNodeActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(DelegateByNodeTypeActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(CopyContentActionDefinition.class)
				.targetPackage(actionPackage)
		);
		config.add(new Entry()
				.classToExtend(ZipUploadActionDefinition.class)
				.targetPackage(actionPackage)
		);
		//////////////////////////////////////////////
		// Column Definitions
		//////////////////////////////////////////////
		String columnPackage = TARGET_PACKAGE_BASE + "column";
		config.add(new Entry()
				.classToExtend(BooleanPropertyColumnDefinition.class)
				.targetPackage(columnPackage)
		);
		config.add(new Entry()
				.classToExtend(MetaDataColumnDefinition.class)
				.targetPackage(columnPackage)
		);
		config.add(new Entry()
				.classToExtend(NodeTypeColumnDefinition.class)
				.targetPackage(columnPackage)
		);
		config.add(new Entry()
				.classToExtend(PropertyColumnDefinition.class)
				.targetPackage(columnPackage)
		);
		config.add(new Entry()
				.classToExtend(PropertyTypeColumnDefinition.class)
				.targetPackage(columnPackage)
		);
		config.add(new Entry()
				.classToExtend(StatusColumnDefinition.class)
				.targetPackage(columnPackage)
		);
		//////////////////////////////////////////////
		// ContentConnector Definitions
		//////////////////////////////////////////////
		String contentConnectorPackage = TARGET_PACKAGE_BASE + "contentconnector";
		config.add(new Entry()
				.classToExtend(ConfiguredContentConnectorDefinition.class)
				.targetPackage(contentConnectorPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredJcrContentConnectorDefinition.class)
				.targetPackage(contentConnectorPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredNodeTypeDefinition.class)
				.targetPackage(contentConnectorPackage)
		);
		//////////////////////////////////////////////
		// Choose Dialog Definitions
		//////////////////////////////////////////////
		String chooseDialogPackage = TARGET_PACKAGE_BASE + "choosedialog";
		config.add(new Entry()
				.classToExtend(ConfiguredChooseDialogDefinition.class)
				.targetPackage(chooseDialogPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredFieldDefinition.class)
				.targetPackage(chooseDialogPackage)
		);
		config.add(new Entry()
				.classToExtend(WorkbenchFieldDefinition.class)
				.targetPackage(chooseDialogPackage)
		);
		config.add(new Entry()
				.classToExtend(LinkFieldSelectionDefinition.class)
				.targetPackage(chooseDialogPackage)
		);
		//////////////////////////////////////////////
		// App Permissions Dialog Definitions
		//////////////////////////////////////////////
		String appPermissionPackage = TARGET_PACKAGE_BASE + "permission";
		config.add(new Entry()
				.classToExtend(ConfiguredAccessDefinition.class)
				.targetPackage(appPermissionPackage)
		);
		config.add(new Entry()
				.classToExtend(ConfiguredOperationPermissionDefinition.class)
				.targetPackage(appPermissionPackage)
		);
		config.add(new Entry()
				.classToExtend(VoterBasedConfiguredAccessDefinition.class)
				.targetPackage(appPermissionPackage)
		);
		//////////////////////////////////////////////
		// Custom Action Definitions
		//////////////////////////////////////////////
		String customPackage = TARGET_PACKAGE_BASE + "custom";
		config.add(new Entry()
				.classToExtend(NodeTypeToActionDelegatingActionDefinition.class)
				.targetPackage(customPackage)
		);
	}

	public static List<Entry> getConfig() {
		return new DefinitionExtenderConfig().config;
	}

	static class Entry {
		private static final String BUILDER_SUFFIX = "Builder";
		private static final String DESCRIPTOR = "Descriptor";
		private static final String DEFINITION = "Definition";
		private static final String CONFIGURED = "Configured";

		private Class classToExtend;
		private String targetPackage;
		private String targetSourceRootPath = DefinitionExtenderConfig.SOURCE_ROOT_PATH;
		private String builderClassName;
		private boolean isDeprecated;

		Class getClassToExtend() {
			return classToExtend;
		}

		void setClassToExtend(Class classToExtend) {
			this.classToExtend = classToExtend;
		}

		Entry classToExtend(Class classToExtend) {
			setClassToExtend(classToExtend);
			return this;
		}

		String getTargetPackage() {
			return targetPackage;
		}

		void setTargetPackage(String targetPackage) {
			this.targetPackage = targetPackage;
		}

		Entry targetPackage(String targetPackage) {
			setTargetPackage(targetPackage);
			return this;
		}

		String getTargetSourceRootPath() {
			return targetSourceRootPath;
		}

		void setTargetSourceRootPath(String targetSourceRootPath) {
			this.targetSourceRootPath = targetSourceRootPath;
		}

		Entry targetSourceRootPath(String targetSourceRootPath) {
			setTargetSourceRootPath(targetSourceRootPath);
			return this;
		}

		String getBuilderClassName() {
			if (StringUtils.isNotEmpty(builderClassName)) {
				return builderClassName;
			}
			String className = classToExtend.getSimpleName();
			String baseName = RegExUtils.removeAll(className, String.join("|", DEFINITION, DESCRIPTOR, CONFIGURED));
			return baseName + BUILDER_SUFFIX;
		}

		void setBuilderClassName(String builderClassName) {
			this.builderClassName = builderClassName;
		}

		Entry builderClassName(String builderClassName) {
			setBuilderClassName(builderClassName);
			return this;
		}

		public boolean isDeprecated() {
			return isDeprecated;
		}

		public void setDeprecated(boolean deprecated) {
			isDeprecated = deprecated;
		}

		public Entry isDeprecated(boolean isDeprecated) {
			setDeprecated(isDeprecated);
			return this;
		}
	}
}

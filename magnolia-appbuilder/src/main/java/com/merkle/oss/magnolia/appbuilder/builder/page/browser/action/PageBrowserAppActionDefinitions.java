package com.merkle.oss.magnolia.appbuilder.builder.page.browser.action;

import info.magnolia.jcr.util.NodeTypes;

import java.util.List;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinitions;
import com.merkle.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.page.EditPageAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.ContentAppContextMenuDefinition;

public class PageBrowserAppActionDefinitions {

	public static List<AppActionGroupDefinition> pageActions(final String appName, final String pageSubAppName) {
		return List.of(
				new AppActionGroupDefinition("editActions", AppActionDefinitions.add(
						AppActionDefinitions.EDIT_ACTIONS,
						new EditDetailPageAppActionDefinition(appName, pageSubAppName),
						new EditPageAppActionDefinition()
				)),
				new AppActionGroupDefinition("activationActions", AppActionDefinitions.ACTIVATION_NON_RECURSIVE),
				new AppActionGroupDefinition("importExportActions", AppActionDefinitions.IMPORT_EXPORT)
		);
	}
	public static AppContextMenuDefinition page(final String appName, final String pageSubAppName) {
		return new ContentAppContextMenuDefinition(
				NodeTypes.Page.NAME,
				new EditDetailPageAppActionDefinition(appName, pageSubAppName),
				pageActions(appName, pageSubAppName)
		);
	}
}

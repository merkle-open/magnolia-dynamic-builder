package com.merkle.oss.magnolia.appbuilder.builder.page.detail.action;

import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.ui.api.action.ActionDefinition;

import java.util.List;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.area.EditAreaAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component.AddComponentAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component.DeleteComponentAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component.DuplicateComponentAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component.EditComponentAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component.SortComponentAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component.StartMoveComponentAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component.StopMoveComponentAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.page.EditPageAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.ContentAppContextMenuDefinition;

public class PageAppActionDefinitions {

	public static final List<AppActionGroupDefinition> PAGE_NODE_ACTIONS = List.of(
			new AppActionGroupDefinition("edit", new EditPageAppActionDefinition())
	);
	public static final List<AppActionGroupDefinition> AREA_NODE_ACTIONS = List.of(
			new AppActionGroupDefinition("addingActions", new AddComponentAppActionDefinition()),
			new AppActionGroupDefinition("edit", new EditAreaAppActionDefinition())
	);
	public static final List<AppActionGroupDefinition> COMPONENT_NODE_ACTIONS = List.of(
			new AppActionGroupDefinition("edit",
					new EditComponentAppActionDefinition(),
					new DuplicateComponentAppActionDefinition(),
					new StartMoveComponentAppActionDefinition(),
					new StopMoveComponentAppActionDefinition(),
					new SortComponentAppActionDefinition()
			),
			new AppActionGroupDefinition("delete",
					new DeleteComponentAppActionDefinition(),
					new DeleteElementAppActionDefinition()
			)
	);

	public static final AppContextMenuDefinition PAGE_NODE_CONTEXT_MENU = new PageAppContextMenuDefinition(
			NodeTypes.Page.NAME,
			new EditPageAppActionDefinition(),
			PAGE_NODE_ACTIONS
	);
	public static final AppContextMenuDefinition AREA_NODE_CONTEXT_MENU = new PageAppContextMenuDefinition(
			NodeTypes.Area.NAME,
			new EditAreaAppActionDefinition(),
			AREA_NODE_ACTIONS
	);
	public static final AppContextMenuDefinition COMPONENT_NODE_CONTEXT_MENU = new PageAppContextMenuDefinition(
			NodeTypes.Component.NAME,
			new EditComponentAppActionDefinition(),
			COMPONENT_NODE_ACTIONS
	);

	public static final List<AppContextMenuDefinition> DEFAULT_CONTEXT_MENUS = List.of(
			PAGE_NODE_CONTEXT_MENU,
			AREA_NODE_CONTEXT_MENU,
			COMPONENT_NODE_CONTEXT_MENU
	);

	private static class PageAppContextMenuDefinition extends ContentAppContextMenuDefinition {
		private PageAppContextMenuDefinition(
				final String nodeType,
				final AppActionDefinition doubleClickAction,
				final List<AppActionGroupDefinition> actionGroups
		) {
			super(nodeType, doubleClickAction, actionGroups);
		}
		@Override
		protected String getUniqueName(final ActionDefinition action) {
			return action.getName();
		}
	}
}

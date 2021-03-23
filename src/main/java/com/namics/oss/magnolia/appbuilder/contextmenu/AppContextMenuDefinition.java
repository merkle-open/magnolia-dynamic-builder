package com.namics.oss.magnolia.appbuilder.contextmenu;

import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.api.action.ActionDefinition;

import java.util.stream.Stream;

public interface AppContextMenuDefinition {
	Stream<ActionbarSectionDefinition> sections();

	Stream<ActionDefinition> actions();
}

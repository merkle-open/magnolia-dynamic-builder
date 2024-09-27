package com.merkle.oss.magnolia.appbuilder.contextmenu;

import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.stream.Stream;

public interface AppContextMenuDefinition {
	Stream<ActionbarSectionDefinition> sections(DropConstraintDefinition dropConstraint);

	Stream<ActionDefinition> actions(DropConstraintDefinition dropConstraint);
}

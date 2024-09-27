package com.merkle.oss.magnolia.appbuilder.contextmenu;

import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarSectionDefinition;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.merkle.oss.magnolia.appbuilder.action.AppActionGroupDefinition;

public class RootAppContextMenuDefinition extends AbstractAppContextMenuDefinition implements AppContextMenuDefinition {

	public RootAppContextMenuDefinition(final List<AppActionGroupDefinition> actionGroups) {
		super(
				() -> "root",
				() -> {
					final ConfiguredAvailabilityDefinition definition = new ConfiguredAvailabilityDefinition();
					definition.setRoot(true);
					definition.setNodes(false);
					definition.setMultiple(true);
					return definition;
				},
				actionGroups
		);
	}

	@Override
	public Stream<ActionbarSectionDefinition> sections(final DropConstraintDefinition dropConstraint) {
		final ConfiguredActionbarSectionDefinition sectionDefinition = new ConfiguredActionbarSectionDefinition();
		sectionDefinition.setName(uniqueNameProvider.get());
		sectionDefinition.setGroups(actionbarGroupDefinitions(false, dropConstraint).collect(Collectors.toList()));
		sectionDefinition.setAvailability(availabilityDefinitionProvider.get());
		return Stream.of(sectionDefinition);
	}
}

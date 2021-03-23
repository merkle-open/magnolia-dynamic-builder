package com.namics.oss.magnolia.appbuilder.contextmenu;

import com.namics.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.actionbar.ActionbarSectionBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RootAppContextMenuDefinition extends AbstractAppContextMenuDefinition implements AppContextMenuDefinition {

	public RootAppContextMenuDefinition(final List<AppActionGroupDefinition> actionGroups) {
		super(
				() -> "root",
				() -> new AvailabilityBuilder()
						.root(true)
						.nodes(false),
				actionGroups
		);
	}

	@Override
	public Stream<ActionbarSectionDefinition> sections() {
		return Stream.of(
				new ActionbarSectionBuilder()
						.name(uniqueNameProvider.get())
						.groups(actionbarGroupDefinitions(false).collect(Collectors.toList()))
						.availability(availabilityBuilderProvider.get())
		);
	}
}

package com.namics.oss.magnolia.appbuilder.contextmenu;

import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.actionbar.ActionbarGroupBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.helper.actionbar.SimpleActionBarItem;
import info.magnolia.ui.actionbar.definition.ActionbarGroupDefinition;
import info.magnolia.ui.actionbar.definition.ActionbarItemDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.api.availability.AvailabilityDefinition;

import javax.inject.Provider;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractAppContextMenuDefinition implements AppContextMenuDefinition {
	protected final Provider<String> uniqueNameProvider;
	protected final Provider<AvailabilityBuilder> availabilityBuilderProvider;
	private final List<AppActionGroupDefinition> actionGroups;

	protected AbstractAppContextMenuDefinition(
			final Provider<String> uniqueNameProvider,
			final Provider<AvailabilityBuilder> availabilityBuilderProvider,
			final List<AppActionGroupDefinition> actionGroups) {
		this.uniqueNameProvider = uniqueNameProvider;
		this.availabilityBuilderProvider = availabilityBuilderProvider;
		this.actionGroups = actionGroups;
	}

	protected Stream<ActionbarGroupDefinition> actionbarGroupDefinitions(final boolean multiple) {
		return actionGroups.stream()
				.map(group ->
						(ActionbarGroupDefinition) new ActionbarGroupBuilder()
								.name(group.name())
								.items(actionbarItems(group.actions(), multiple))
				)
				.filter(group -> !group.getItems().isEmpty());
	}

	private List<ActionbarItemDefinition> actionbarItems(final List<AppActionDefinition> actions, final boolean multiple) {
		return actions.stream()
				.filter(browserActionDefinition -> !browserActionDefinition.isCallback())
				.filter(browserActionDefinition -> browserActionDefinition.multiple() || multiple == browserActionDefinition.multiple())
				.map(AppActionDefinition::action)
				.map(this::getUniqueName)
				.map(SimpleActionBarItem::name)
				.collect(Collectors.toList());
	}

	@Override
	public Stream<ActionDefinition> actions() {
		return actionGroups.stream()
				.flatMap(group -> group.actions().stream())
				.map(this::map);
	}

	private ActionDefinition map(final AppActionDefinition browserAction) {
		// Somehow actionDefinition must be the same instance (Magnolia magic?!). Otherwise the execution in OpenCreateDialogAction will fail.
		final ConfiguredActionDefinition action = browserAction.action();
		if (!browserAction.isCallback()) {
			action.setName(getUniqueName(action));
			action.setAvailability(merge(action.getAvailability(), browserAction));
		}
		return action;
	}

	private AvailabilityDefinition merge(final AvailabilityDefinition availability, final AppActionDefinition browserAction) {
		return availabilityBuilderProvider.get()
				.access(availability.getAccess())
				.multiple(browserAction.multiple())
				.writePermissionRequired(availability.isWritePermissionRequired())
				.rules(availability.getRules());
	}

	protected String getUniqueName(final ActionDefinition action) {
		return uniqueNameProvider.get() + "." + action.getName();
	}
}

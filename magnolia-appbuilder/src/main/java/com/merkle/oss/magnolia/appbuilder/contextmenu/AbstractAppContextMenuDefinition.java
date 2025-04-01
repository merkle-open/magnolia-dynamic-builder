package com.merkle.oss.magnolia.appbuilder.contextmenu;

import info.magnolia.ui.actionbar.definition.ActionbarGroupDefinition;
import info.magnolia.ui.actionbar.definition.ActionbarItemDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarGroupDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Provider;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.browser.actionbar.SimpleActionBarItem;

public abstract class AbstractAppContextMenuDefinition implements AppContextMenuDefinition {
	protected final Provider<String> uniqueNameProvider;
	protected final Provider<ConfiguredAvailabilityDefinition> availabilityDefinitionProvider;
	private final List<AppActionGroupDefinition> actionGroups;

	protected AbstractAppContextMenuDefinition(
			final Provider<String> uniqueNameProvider,
			final Provider<ConfiguredAvailabilityDefinition> availabilityDefinitionProvider,
			final List<AppActionGroupDefinition> actionGroups) {
		this.uniqueNameProvider = uniqueNameProvider;
		this.availabilityDefinitionProvider = availabilityDefinitionProvider;
		this.actionGroups = actionGroups;
	}

	protected Stream<ActionbarGroupDefinition> actionbarGroupDefinitions(final boolean multiple, final DropConstraintDefinition dropConstraint) {
		return actionGroups.stream()
				.map(group -> {
					final ConfiguredActionbarGroupDefinition definition = new ConfiguredActionbarGroupDefinition();
					definition.setName(group.name());
					definition.setItems(actionbarItems(group.actions(), multiple, dropConstraint));
					return (ActionbarGroupDefinition)definition;
				})
				.filter(group -> !group.getItems().isEmpty());
	}

	private List<ActionbarItemDefinition> actionbarItems(final List<AppActionDefinition> actions, final boolean multiple, final DropConstraintDefinition dropConstraint) {
		return actions.stream()
				.filter(browserActionDefinition -> !browserActionDefinition.isCallback())
				.filter(browserActionDefinition -> browserActionDefinition.multiple() || multiple == browserActionDefinition.multiple())
				.map(appAction -> appAction.action(dropConstraint))
				.map(this::getUniqueName)
				.map(SimpleActionBarItem::name)
				.collect(Collectors.toList());
	}

	@Override
	public Stream<ActionDefinition> actions(final DropConstraintDefinition dropConstraint) {
		return actionGroups.stream()
				.flatMap(group -> group.actions().stream())
				.map(action -> map(action, dropConstraint));
	}

	private ActionDefinition map(final AppActionDefinition browserAction, final DropConstraintDefinition dropConstraint) {
		// Somehow actionDefinition must be the same instance (Magnolia magic?!). Otherwise the execution in OpenCreateDialogAction will fail.
		final ConfiguredActionDefinition action = browserAction.action(dropConstraint);
		if (!browserAction.isCallback()) {
			action.setName(getUniqueName(action));
			action.setAvailability(merge(action.getAvailability(), browserAction));
		}
		return action;
	}

	private AvailabilityDefinition merge(final AvailabilityDefinition availability, final AppActionDefinition browserAction) {
		final ConfiguredAvailabilityDefinition definition = availabilityDefinitionProvider.get();
		definition.setAccess(availability.getAccess());
		definition.setMultiple(browserAction.multiple());
		definition.setWritePermissionRequired(availability.isWritePermissionRequired());
		definition.setRules(availability.getRules());
		return definition;

	}

	protected String getUniqueName(final ActionDefinition action) {
		return uniqueNameProvider.get() + "." + action.getName();
	}
}

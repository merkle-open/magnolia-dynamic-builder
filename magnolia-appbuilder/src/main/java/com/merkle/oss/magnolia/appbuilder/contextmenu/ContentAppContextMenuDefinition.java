package com.merkle.oss.magnolia.appbuilder.contextmenu;

import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarSectionDefinition;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.merkle.oss.magnolia.appbuilder.action.DoubleClickAction;

public class ContentAppContextMenuDefinition extends AbstractAppContextMenuDefinition implements AppContextMenuDefinition {
	private static final Pattern NODE_TYPE_NAME_PATTERN = Pattern.compile("^[^:]+:(.*)$");
	private final String nodeType;
	@Nullable
	private final AppActionDefinition doubleClickAction;

	public ContentAppContextMenuDefinition(
			final String nodeType,
			@Nullable final AppActionDefinition doubleClickAction,
			final List<AppActionGroupDefinition> actionGroups) {
		super(
				() -> {
					final Matcher matcher = NODE_TYPE_NAME_PATTERN.matcher(nodeType);
					if(matcher.matches()) {
						return matcher.group(1);
					}
					return nodeType;
				},
				() -> {
					final ConfiguredAvailabilityDefinition definition = new ConfiguredAvailabilityDefinition();
					definition.setRoot(false);
					definition.setNodes(true);
					definition.setMultiple(true);
					definition.addNodeType(nodeType);
					return definition;
				},
				actionGroups
		);
		this.nodeType = nodeType;
		this.doubleClickAction = doubleClickAction;
	}

	@Override
	public Stream<ActionbarSectionDefinition> sections(final DropConstraintDefinition dropConstraint) {
		final ConfiguredActionbarSectionDefinition singleSectionDefinition = new ConfiguredActionbarSectionDefinition();
		singleSectionDefinition.setName(uniqueNameProvider.get());
		singleSectionDefinition.setGroups(actionbarGroupDefinitions(false, dropConstraint).collect(Collectors.toList()));
		singleSectionDefinition.setAvailability(availabilityDefinitionProvider.get());

		final ConfiguredActionbarSectionDefinition multipleSectionDefinition = new ConfiguredActionbarSectionDefinition();
		multipleSectionDefinition.setName(uniqueNameProvider.get());
		multipleSectionDefinition.setGroups(actionbarGroupDefinitions(false, dropConstraint).collect(Collectors.toList()));
		multipleSectionDefinition.setAvailability(availabilityDefinitionProvider.get());

		return Stream.of(singleSectionDefinition, multipleSectionDefinition);
	}

	public Optional<DoubleClickAction> doubleClickAction(final DropConstraintDefinition dropConstraint) {
		return Optional.ofNullable(doubleClickAction).map(action ->
				new DoubleClickAction() {
					@Override
					public String nodeType() {
						return nodeType;
					}
					@Override
					public String action() {
						return getUniqueName(action.action(dropConstraint));
					}
				}
		);
	}
}

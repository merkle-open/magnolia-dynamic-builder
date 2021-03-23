package com.namics.oss.magnolia.appbuilder.contextmenu;

import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.namics.oss.magnolia.appbuilder.action.DoubleClickAction;
import com.namics.oss.magnolia.appbuilder.builder.generated.actionbar.ActionbarSectionBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityRuleBuilder;
import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.framework.availability.IsNotDeletedRule;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContentAppContextMenuDefinition extends AbstractAppContextMenuDefinition implements AppContextMenuDefinition {
	private final String nodeType;
	@Nullable
	private final AppActionDefinition doubleClickAction;

	public ContentAppContextMenuDefinition(
			final String nodeType,
			@Nullable final AppActionDefinition doubleClickAction,
			final List<AppActionGroupDefinition> actionGroups) {
		super(
				() -> nodeType.replaceFirst("mgnl:", ""),
				() ->
						new AvailabilityBuilder()
								.root(false)
								.nodes(true)
								.nodeTypes(nodeType)
								.rules(new AvailabilityRuleBuilder().implementationClass(IsNotDeletedRule.class)),
				actionGroups
		);
		this.nodeType = nodeType;
		this.doubleClickAction = doubleClickAction;
	}

	@Override
	public Stream<ActionbarSectionDefinition> sections() {
		return Stream.of(
				new ActionbarSectionBuilder()
						.name(uniqueNameProvider.get())
						.groups(actionbarGroupDefinitions(false).collect(Collectors.toList()))
						.availability(availabilityBuilderProvider.get()),
				new ActionbarSectionBuilder()
						.name(uniqueNameProvider.get() + "Multiple")
						.groups(actionbarGroupDefinitions(true).collect(Collectors.toList()))
						.availability(availabilityBuilderProvider.get().multiple(true))
		);
	}

	public Optional<DoubleClickAction> doubleClickAction() {
		return Optional.ofNullable(doubleClickAction).map(action ->
				new DoubleClickAction() {
					@Override
					public String nodeType() {
						return nodeType;
					}

					@Override
					public String action() {
						return getUniqueName(action.action());
					}
				}
		);
	}
}

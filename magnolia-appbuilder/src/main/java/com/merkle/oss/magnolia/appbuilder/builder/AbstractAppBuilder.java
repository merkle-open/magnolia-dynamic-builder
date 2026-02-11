package com.merkle.oss.magnolia.appbuilder.builder;

import com.merkle.oss.magnolia.appbuilder.action.DoubleClickAction;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.browser.action.NodeTypeToActionDelegatingActionDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.ContentAppContextMenuDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import info.magnolia.ui.actionbar.definition.ActionbarDefinition;
import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractAppBuilder {

    protected ActionbarDefinition actionbar(final List<AppContextMenuDefinition> contextMenuDefinitions, final DropConstraintDefinition dropConstraint) {
        final List<ActionbarSectionDefinition> sections = contextMenuDefinitions.stream()
                .flatMap(contextMenu -> contextMenu.sections(dropConstraint))
                .collect(Collectors.toList());
        final ConfiguredActionbarDefinition definition = new ConfiguredActionbarDefinition();
        definition.setDefaultAction("defaultAction");
        definition.setSections(sections);
        return definition;
    }

    protected Map<String, ActionDefinition> actions(final List<AppContextMenuDefinition> contextMenuDefinitions, final DropConstraintDefinition dropConstraint) {
        final Map<String, String> doubleClickNodeTypeActions = contextMenuDefinitions.stream()
                .filter( definition -> getContextMenuDefinitionType().isInstance(definition))
                .map(definition -> getContextMenuDefinitionType().cast(definition))
                .map(contextMenu -> contextMenu.doubleClickAction(dropConstraint))
                .flatMap(Optional::stream)
                .collect(Collectors.toMap(DoubleClickAction::nodeType, DoubleClickAction::action));

        final NodeTypeToActionDelegatingActionDefinition defaultAction = new NodeTypeToActionDelegatingActionDefinition();
        defaultAction.setName("defaultAction");
        defaultAction.setNodeTypeActionMapping(doubleClickNodeTypeActions);
        defaultAction.setAvailability(new AvailabilityDefinitionBuilder().rule(new JcrIsNotDeletedRuleDefinition()).build());
        return Stream
                .concat(
                        Stream.of(defaultAction),
                        contextMenuDefinitions.stream().flatMap(contextMenu -> contextMenu.actions(dropConstraint))
                )
                .collect(Collectors.toMap(ActionDefinition::getName, action -> action, (t, t2) -> t)); //overwrite duplicate keys (==action name)
    }

    protected Class<? extends ContentAppContextMenuDefinition> getContextMenuDefinitionType() {
        return ContentAppContextMenuDefinition.class;
    }
}

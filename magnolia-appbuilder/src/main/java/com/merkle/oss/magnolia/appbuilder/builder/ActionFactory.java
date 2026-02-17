package com.merkle.oss.magnolia.appbuilder.builder;

import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.merkle.oss.magnolia.appbuilder.action.DoubleClickAction;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.browser.action.NodeTypeToActionDelegatingActionDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.ContentAppContextMenuDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

public class ActionFactory {
    public Map<String, ActionDefinition> create(final List<AppContextMenuDefinition> contextMenuDefinitions, final DropConstraintDefinition dropConstraint) {
        final Map<String, String> doubleClickNodeTypeActions = contextMenuDefinitions.stream()
                .filter(ContentAppContextMenuDefinition.class::isInstance)
                .map(ContentAppContextMenuDefinition.class::cast)
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
}

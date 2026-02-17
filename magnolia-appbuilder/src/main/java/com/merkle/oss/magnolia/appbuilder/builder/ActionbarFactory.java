package com.merkle.oss.magnolia.appbuilder.builder;

import info.magnolia.ui.actionbar.definition.ActionbarDefinition;
import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.List;
import java.util.stream.Collectors;

import com.merkle.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;

public class ActionbarFactory {
    public ActionbarDefinition create(final List<AppContextMenuDefinition> contextMenuDefinitions, final DropConstraintDefinition dropConstraint) {
        final List<ActionbarSectionDefinition> sections = contextMenuDefinitions.stream()
                .flatMap(contextMenu -> contextMenu.sections(dropConstraint))
                .collect(Collectors.toList());
        final ConfiguredActionbarDefinition definition = new ConfiguredActionbarDefinition();
        definition.setDefaultAction("defaultAction");
        definition.setSections(sections);
        return definition;
    }
}

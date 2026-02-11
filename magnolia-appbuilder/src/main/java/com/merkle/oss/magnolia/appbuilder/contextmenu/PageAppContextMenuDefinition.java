package com.merkle.oss.magnolia.appbuilder.contextmenu;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class PageAppContextMenuDefinition extends ContentAppContextMenuDefinition {

    public PageAppContextMenuDefinition(String nodeType, @Nullable AppActionDefinition doubleClickAction, List<AppActionGroupDefinition> actionGroups) {
        super(nodeType, doubleClickAction, actionGroups);
    }

    @Override
    protected String getUniqueName(final ActionDefinition action) {
        return action.getName();
    }
}

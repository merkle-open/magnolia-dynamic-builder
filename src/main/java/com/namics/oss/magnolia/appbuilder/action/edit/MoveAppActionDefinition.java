package com.namics.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.i18nsystem.I18nizer;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.action.AbstractAction;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.api.availability.AvailabilityRuleDefinition;
import info.magnolia.ui.availability.rule.CanMoveRuleDefinition;
import info.magnolia.ui.chooser.definition.ChooserDefinition;
import info.magnolia.ui.contentapp.Datasource;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;
import info.magnolia.ui.framework.overlay.ChooserController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.vaadin.shared.ui.grid.DropLocation;

public class MoveAppActionDefinition implements AppActionDefinition {
    private final String icon;
    private final String label;

    public MoveAppActionDefinition() {
        this(MagnoliaIcons.MOVE.getCssClass(), "actions.move");
    }

    public MoveAppActionDefinition(final String icon, final String label) {
        this.icon = icon;
        this.label = label;
    }

    @Override
    public MoveAction.Definition action(final DropConstraintDefinition dropConstraint) {
        final MoveAction.Definition definition = new MoveAction.Definition(dropConstraint);
        definition.setName("move");
        definition.setLabel(label);
        definition.setIcon(icon);
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new JcrIsNotDeletedRuleDefinition())
                .rule(new PermissionRequiredRuleDefinition(Permission.ALL))
                .build()
        );
        return definition;
    }

    public static class MoveAction<T> extends AbstractAction<MoveAction.Definition> {
        private final ChooserController chooserController;
        private final Datasource<T> datasource;
        private final ValueContext<T> valueContext;
        private final DialogDefinitionRegistry dialogDefinitionRegistry;
        private final I18nizer i18nizer;

        @Inject
        public MoveAction(
                final Definition actionDefinition,
                final ChooserController chooserController,
                final Datasource<T> datasource,
                final ValueContext<T> valueContext,
                final DialogDefinitionRegistry dialogDefinitionRegistry,
                final I18nizer i18nizer
        ) {
            super(actionDefinition);
            this.chooserController = chooserController;
            this.datasource = datasource;
            this.valueContext = valueContext;
            this.dialogDefinitionRegistry = dialogDefinitionRegistry;
            this.i18nizer = i18nizer;
        }

        @Override
        public void execute() {
            final ChooserDefinition<T, ?> chooser = (ChooserDefinition<T, ?>) i18nizer.decorate(dialogDefinitionRegistry.getProvider("magnolia-appbuilder:move").get());
            chooser.setActions(chooser.getActions().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> map(entry.getValue()))));
            final List<T> items = valueContext.get().collect(Collectors.toList());
            final ChooserController.OnItemChosen<T> onItemChosen = chooserController.openChooser(chooser, items.stream().findFirst().orElse(null));
            onItemChosen
                    .whenComplete((chooseResult, err) -> {
                        if (err != null) {
                            throw new RuntimeException(err);
                        }
                        final T target = chooseResult.getChoice().orElse(datasource.getRoot());
                        final DropLocation dropLocation = Optional.ofNullable(onItemChosen.getAction())
                                .flatMap(action -> Stream.of(DropLocation.values()).filter(location -> StringUtils.equalsIgnoreCase(location.name(), action)).findFirst())
                                .orElse(DropLocation.ON_TOP);
                        datasource.moveItems(items, target, dropLocation);
                        valueContext.clear();
                        valueContext.set(new HashSet<>(items));
                    });
        }

        private ActionDefinition map(final ActionDefinition actionDefinition) {
            if(actionDefinition instanceof ConfiguredActionDefinition) {
                ((ConfiguredActionDefinition)actionDefinition).setAvailability(
                        new AvailabilityDefinitionBuilder(actionDefinition.getAvailability())
                                .rules(actionDefinition.getAvailability().getRules().stream().map(this::map).collect(Collectors.toList()))
                                .build()
                );
            }
            return actionDefinition;
        }

        private AvailabilityRuleDefinition map(final AvailabilityRuleDefinition actionDefinition) {
            if (actionDefinition instanceof CanMoveRuleDefinition) {
                final CanMoveRuleDefinition canMoveRule = (CanMoveRuleDefinition) actionDefinition;
                canMoveRule.setDropConstraint(getDefinition().getDropConstraintDefinition());
                return canMoveRule;
            }
            return actionDefinition;
        }

        public static class Definition extends ConfiguredActionDefinition {
            private final DropConstraintDefinition dropConstraintDefinition;

            public Definition(final DropConstraintDefinition dropConstraintDefinition) {
                this.dropConstraintDefinition = dropConstraintDefinition;
                setImplementationClass(MoveAction.class);
            }

            public DropConstraintDefinition getDropConstraintDefinition() {
                return dropConstraintDefinition;
            }
        }
    }
}

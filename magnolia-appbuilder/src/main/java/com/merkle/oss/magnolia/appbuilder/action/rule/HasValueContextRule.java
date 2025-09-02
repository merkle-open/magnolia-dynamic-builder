package com.merkle.oss.magnolia.appbuilder.action.rule;

import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.availability.AvailabilityRule;
import info.magnolia.ui.api.availability.AvailabilityRuleType;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityRuleDefinition;

import java.util.Collection;

import jakarta.inject.Inject;

public class HasValueContextRule implements AvailabilityRule {
    private final ValueContext<?> valueContext;

    @Inject
    public HasValueContextRule(final ValueContext<?> valueContext) {
        this.valueContext = valueContext;
    }

    @Override
    public boolean isAvailable(final Collection<?> itemIds) {
        return valueContext.getSingle().isPresent();
    }

    @AvailabilityRuleType("hasValueContextRule")
    public static class Definition extends ConfiguredAvailabilityRuleDefinition {
        public Definition() {
            setImplementationClass(HasValueContextRule.class);
        }
    }
}

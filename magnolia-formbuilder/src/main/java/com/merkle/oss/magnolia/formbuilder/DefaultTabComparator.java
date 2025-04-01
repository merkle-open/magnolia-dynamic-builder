package com.merkle.oss.magnolia.formbuilder;

import info.magnolia.ui.framework.layout.ConfiguredTabDefinition;

import java.util.Comparator;
import java.util.Optional;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.merkle.oss.magnolia.formbuilder.annotation.TabOrder;


public class DefaultTabComparator implements Comparator<ConfiguredTabDefinition> {
    @Nullable
    private final String[] order;

    public DefaultTabComparator(@Nullable final TabOrder tabOrder) {
        this.order = Optional.ofNullable(tabOrder).map(TabOrder::value).orElse(null);
    }

    @Override
    public int compare(final ConfiguredTabDefinition tab1, final ConfiguredTabDefinition tab2) {
        return ArrayUtils.indexOf(order, tab1.getName()) - ArrayUtils.indexOf(order, tab2.getName());
    }

    public static class Factory implements FormFactory.TabComparatorFactory {
        @Override
        public Comparator<ConfiguredTabDefinition> create(@Nullable final TabOrder tabOrder) {
            return new DefaultTabComparator(tabOrder);
        }
    }
}

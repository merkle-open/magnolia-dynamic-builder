package com.namics.oss.magnolia.appbuilder.builder;

import com.vaadin.data.ValueProvider;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.ui.contentapp.configuration.column.ColumnType;
import info.magnolia.ui.contentapp.configuration.column.DateColumnDefinition;

import javax.jcr.Item;
import javax.jcr.Node;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@ColumnType("modificationDateColumn")
public class ModificationDateColumnDefinition extends DateColumnDefinition {

	public ModificationDateColumnDefinition() {
		setName("modificationDateColumn");
		setLabel("views.moddate");
		setWidth(160d);
		setSortable(true);
		setValueProvider((Class)ModificationDateValueProvider.class); // ConfiguredColumnDefinition.valueProvider has wrong types!!
	}

	public static class ModificationDateValueProvider implements ValueProvider<Item, Date> {
		@Override
		public Date apply(final Item node) {
			try {
				return Optional
						.ofNullable(NodeTypes.LastModified.getLastModified((Node) node))
						.map(Calendar::getTime)
						.orElse(null);
			} catch (Exception e) {
				return null;
			}
		}
	}
}
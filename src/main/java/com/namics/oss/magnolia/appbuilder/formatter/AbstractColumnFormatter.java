package com.namics.oss.magnolia.appbuilder.formatter;

import com.namics.oss.magnolia.powernode.PowerNode;
import com.namics.oss.magnolia.powernode.PowerNodeService;
import com.vaadin.v7.ui.Table;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;
import org.apache.commons.lang3.StringUtils;

import javax.jcr.Item;
import javax.jcr.Node;
import java.util.Optional;

public abstract class AbstractColumnFormatter extends info.magnolia.ui.workbench.column.AbstractColumnFormatter<PropertyColumnDefinition> {
	private final PowerNodeService powerNodeService;

	protected AbstractColumnFormatter(
			final PowerNodeService powerNodeService,
			final PropertyColumnDefinition definition) {
		super(definition);
		this.powerNodeService = powerNodeService;
	}

	@Override
	public final Object generateCell(final Table table, final Object itemParam, final Object columnId) {
		return Optional
				.ofNullable(getJcrItem(table, itemParam))
				.filter(Item::isNode)
				.map(item -> (Node) item)
				.map(powerNodeService::convertToPowerNode)
				.map(item ->
						format(item, (String) columnId).orElseGet(item::getName)
				)
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * Formats an item at a column
	 *
	 * @return formatted item. If empty optional is returned, the node name is used.
	 */
	protected abstract Optional<String> format(PowerNode item, String columnId);
}

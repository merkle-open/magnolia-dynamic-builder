package com.namics.oss.magnolia.appbuilder.formatter;

import com.vaadin.data.ValueProvider;
import org.apache.commons.lang3.StringUtils;

import javax.jcr.Item;
import javax.jcr.Node;
import java.util.Optional;

public abstract class AbstractValueProvider implements ValueProvider<Item, String> {

	@Override
	public String apply(final Item item) {
		return Optional
				.of(item)
				.filter(Item::isNode)
				.map(Node.class::cast)
				.flatMap(this::getValue)
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * Formats an item at a column
	 *
	 * @return formatted item. If empty optional is returned, the empty string is used.
	 */
	protected abstract Optional<String> getValue(final Node node);
}

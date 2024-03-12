package com.namics.oss.magnolia.appbuilder.formatter;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Optional;

public class NodeNameValueProvider extends AbstractValueProvider {
	@Override
	protected Optional<String> getValue(final Node node) {
		try {
			return Optional.ofNullable(node.getName());
		} catch (RepositoryException e) {
			return Optional.empty();
		}
	}
}

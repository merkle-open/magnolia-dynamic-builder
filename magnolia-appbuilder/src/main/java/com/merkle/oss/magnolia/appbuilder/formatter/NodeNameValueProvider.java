package com.merkle.oss.magnolia.appbuilder.formatter;

import java.util.Optional;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

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

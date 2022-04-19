package com.namics.oss.magnolia.appbuilder.action.activation.workflow;

import info.magnolia.module.workflow.action.FormTypeDefinition;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FormTypeProvider {
	public static final Map<String, Class<?>> DEFAULT_FORM_TYPES = Map.of(
			"comment", String.class,
			"publicationDate", Date.class
	);

	static List<FormTypeDefinition> getFormTypes(final Map<String, Class<?>> formTypes) {
		return formTypes.entrySet().stream()
				.map(entry ->
					createFormTypeDefinition(entry.getKey(), entry.getValue())
				)
				.collect(Collectors.toList());
	}

	private static FormTypeDefinition createFormTypeDefinition(final String name, final Class<?> type) {
		final FormTypeDefinition formTypeDefinition = new FormTypeDefinition();
		formTypeDefinition.setName(name);
		formTypeDefinition.setType(type);
		return formTypeDefinition;
	}
}

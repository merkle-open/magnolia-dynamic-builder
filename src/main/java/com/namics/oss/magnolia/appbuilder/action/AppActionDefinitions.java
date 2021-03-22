package com.namics.oss.magnolia.appbuilder.action;

import com.namics.oss.magnolia.appbuilder.action.activation.ActivateAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.activation.ActivateRecursiveAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.activation.DeactivateAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.edit.ConfirmDeleteAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.edit.DeleteAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.edit.EditAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.edit.MoveAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.importexport.ExportAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.importexport.ImportAppActionDefinition;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppActionDefinitions {

	public static final List<AppActionDefinition> IMPORT_EXPORT = List.of(
			new ImportAppActionDefinition(),
			new ExportAppActionDefinition()
	);

	public static final List<AppActionDefinition> ACTIVATION = List.of(
			new ActivateAppActionDefinition(),
			new ActivateRecursiveAppActionDefinition(),
			new DeactivateAppActionDefinition()
	);

	public static final List<AppActionDefinition> ACTIVATION_NON_RECURSIVE = List.of(
			new ActivateAppActionDefinition(),
			new DeactivateAppActionDefinition()
	);

	private static final List<AppActionDefinition> EDIT_ACTIONS = List.of(
			new MoveAppActionDefinition(),
			new ConfirmDeleteAppActionDefinition(),
			new DeleteAppActionDefinition()
	);

	public static final List<AppActionDefinition> editActions(final EditAppActionDefinition editAppActionDefinition) {
		return add(
				EDIT_ACTIONS,
				editAppActionDefinition
		);
	}

	public static <T> List<T> add(final List<T> list, T... values) {
		return merge(list, List.of(values));
	}

	public static <T> List<T> merge(final List<T>... lists) {
		return Arrays
				.stream(lists)
				.flatMap(Collection::stream)
				.collect(Collectors.toUnmodifiableList());
	}
}

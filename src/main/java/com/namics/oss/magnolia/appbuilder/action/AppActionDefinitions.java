package com.namics.oss.magnolia.appbuilder.action;

import com.namics.oss.magnolia.appbuilder.action.activation.ActivateAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.activation.ActivateDeletionAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.activation.ActivateRecursiveAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.activation.DeactivateAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.activation.workflow.WorkflowActivateAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.activation.workflow.WorkflowActivateDeletionAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.activation.workflow.WorkflowActivateRecursiveAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.activation.workflow.WorkflowDeactivateAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.edit.*;
import com.namics.oss.magnolia.appbuilder.action.importexport.ExportAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.importexport.ImportAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.version.RestorePreviousVersionAppActionDefinition;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppActionDefinitions {

	public static final List<AppActionDefinition> IMPORT_EXPORT = List.of(
			new ImportAppActionDefinition(),
			new ExportAppActionDefinition()
	);

	public static final List<AppActionDefinition> WORKFLOW_ACTIVATION = List.of(
			new WorkflowActivateAppActionDefinition(),
			new WorkflowActivateRecursiveAppActionDefinition(),
			new WorkflowActivateDeletionAppActionDefinition(),
			new WorkflowDeactivateAppActionDefinition()
	);

	public static final List<AppActionDefinition> WORKFLOW_ACTIVATION_NON_RECURSIVE = List.of(
			new WorkflowActivateAppActionDefinition(),
			new WorkflowActivateDeletionAppActionDefinition(),
			new WorkflowDeactivateAppActionDefinition()
	);

	public static final List<AppActionDefinition> ACTIVATION = List.of(
			new ActivateAppActionDefinition(),
			new ActivateRecursiveAppActionDefinition(),
			new ActivateDeletionAppActionDefinition(),
			new DeactivateAppActionDefinition()
	);

	public static final List<AppActionDefinition> ACTIVATION_NON_RECURSIVE = List.of(
			new ActivateAppActionDefinition(),
			new ActivateDeletionAppActionDefinition(),
			new DeactivateAppActionDefinition()
	);

	public static final List<AppActionDefinition> RESTORE_PREVIOUS_ACTIONS = List.of(
			new RestorePreviousVersionAppActionDefinition()
	);

	public static final List<AppActionDefinition> EDIT_ACTIONS = List.of(
			new MoveAppActionDefinition(),
			new ConfirmDeleteAppActionDefinition(),
			new DeleteAppActionDefinition(),
			new CopyAppActionDefinition(),
			new PasteAppActionDefinition()
	);

	public static final List<AppActionDefinition> EDIT_ACTIONS_MARK_AS_DELETE = List.of(
			new MoveAppActionDefinition(),
			new ConfirmDeleteAppActionDefinition(),
			new MarkAsDeletedAppActionDefinition(),
			new CopyAppActionDefinition(),
			new PasteAppActionDefinition()
	);

	public static final List<AppActionDefinition> editActions(final EditAppActionDefinition editAppActionDefinition) {
		return add(
				EDIT_ACTIONS,
				editAppActionDefinition
		);
	}

	public static final List<AppActionDefinition> editActionsWithMarkAsDeleted(final EditAppActionDefinition editAppActionDefinition) {
		return add(
				EDIT_ACTIONS_MARK_AS_DELETE,
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

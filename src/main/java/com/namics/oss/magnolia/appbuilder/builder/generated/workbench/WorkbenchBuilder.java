package com.namics.oss.magnolia.appbuilder.builder.generated.workbench;

import info.magnolia.ui.workbench.contenttool.ContentToolDefinition;
import info.magnolia.ui.workbench.definition.ConfiguredWorkbenchDefinition;
import info.magnolia.ui.workbench.definition.ContentPresenterDefinition;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.232403"
)
public class WorkbenchBuilder extends ConfiguredWorkbenchDefinition {
	public WorkbenchBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public WorkbenchBuilder dialogWorkbench(boolean dialogWorkbench) {
		this.setDialogWorkbench(dialogWorkbench);
		return this;
	}

	public WorkbenchBuilder editable(boolean editable) {
		this.setEditable(editable);
		return this;
	}

	public WorkbenchBuilder dragAndDrop(boolean dragAndDrop) {
		this.setDragAndDrop(dragAndDrop);
		return this;
	}

	public WorkbenchBuilder dropConstraintClass(Class<? extends DropConstraint> dropConstraintClass) {
		this.setDropConstraintClass(dropConstraintClass);
		return this;
	}

	public WorkbenchBuilder contentViews(List<ContentPresenterDefinition> contentViews) {
		this.setContentViews(contentViews);
		return this;
	}

	public WorkbenchBuilder contentView(ContentPresenterDefinition contentView) {
		this.addContentView(contentView);
		return this;
	}

	public WorkbenchBuilder contentTools(List<ContentToolDefinition> contentTools) {
		this.setContentTools(contentTools);
		return this;
	}

	public WorkbenchBuilder contentViews(ContentPresenterDefinition... contentViews) {
		this.setContentViews(Arrays.asList(contentViews));
		return this;
	}

	public WorkbenchBuilder contentTools(ContentToolDefinition... contentTools) {
		this.setContentTools(Arrays.asList(contentTools));
		return this;
	}
}

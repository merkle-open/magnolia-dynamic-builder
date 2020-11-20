package com.namics.oss.magnolia.appbuilder.builder.generated.permission;

import info.magnolia.cms.security.operations.AccessDefinition;
import info.magnolia.cms.security.operations.ConfiguredOperationPermissionDefinition;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:02.010904"
)
public class OperationPermissionBuilder extends ConfiguredOperationPermissionDefinition {
	public OperationPermissionBuilder delete(AccessDefinition delete) {
		this.setDelete(delete);
		return this;
	}

	public OperationPermissionBuilder write(AccessDefinition write) {
		this.setWrite(write);
		return this;
	}

	public OperationPermissionBuilder read(AccessDefinition read) {
		this.setRead(read);
		return this;
	}

	public OperationPermissionBuilder move(AccessDefinition move) {
		this.setMove(move);
		return this;
	}

	public OperationPermissionBuilder add(AccessDefinition add) {
		this.setAdd(add);
		return this;
	}

	public OperationPermissionBuilder execute(AccessDefinition execute) {
		this.setExecute(execute);
		return this;
	}
}

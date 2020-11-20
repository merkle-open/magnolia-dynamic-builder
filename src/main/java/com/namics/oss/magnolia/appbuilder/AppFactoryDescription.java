package com.namics.oss.magnolia.appbuilder;

import info.magnolia.cms.security.operations.AccessDefinition;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.dialog.definition.ChooseDialogDefinition;

import java.util.Map;

public class AppFactoryDescription {

	private String id;
	private String name;
	private boolean enabled;
	private AppFactoryMetaData factoryMetaData;
	private Map<String, SubAppDescriptor> subApps;
	private ChooseDialogDefinition chooseDialog;
	private AccessDefinition permissions;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AppFactoryDescription id(String id) {
		setId(id);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AppFactoryDescription name(String name) {
		setName(name);
		return this;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public AppFactoryDescription enabled(boolean enabled) {
		setEnabled(enabled);
		return this;
	}

	public AppFactoryMetaData getFactoryMetaData() {
		return factoryMetaData;
	}

	public void setFactoryMetaData(AppFactoryMetaData factoryMetaData) {
		this.factoryMetaData = factoryMetaData;
	}

	public AppFactoryDescription factoryMetaData(AppFactoryMetaData factoryMetaData) {
		setFactoryMetaData(factoryMetaData);
		return this;
	}

	public Map<String, SubAppDescriptor> getSubApps() {
		return subApps;
	}

	public void setSubApps(Map<String, SubAppDescriptor> subApps) {
		this.subApps = subApps;
	}

	public AppFactoryDescription subApps(Map<String, SubAppDescriptor> subApps) {
		setSubApps(subApps);
		return this;
	}

	public ChooseDialogDefinition getChooseDialog() {
		return chooseDialog;
	}

	public void setChooseDialog(ChooseDialogDefinition chooseDialog) {
		this.chooseDialog = chooseDialog;
	}

	public AppFactoryDescription chooseDialog(ChooseDialogDefinition chooseDialog) {
		setChooseDialog(chooseDialog);
		return this;
	}

	public AccessDefinition getPermissions() {
		return permissions;
	}

	public void setPermissions(AccessDefinition permissions) {
		this.permissions = permissions;
	}

	public AppFactoryDescription permissions(AccessDefinition permissions) {
		setPermissions(permissions);
		return this;
	}
}

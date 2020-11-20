package com.namics.oss.magnolia.appbuilder;


public class AppFactoryMetaData {

	private String label;
	private Object factoryObject;
	private String i18nBasename;
	private String icon;
	private String theme;


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public AppFactoryMetaData label(String label) {
		setLabel(label);
		return this;
	}

	public Object getFactoryObject() {
		return factoryObject;
	}

	public void setFactoryObject(Object factoryObject) {
		this.factoryObject = factoryObject;
	}

	public AppFactoryMetaData factoryObject(Object factoryObject) {
		setFactoryObject(factoryObject);
		return this;
	}

	public String getI18nBasename() {
		return i18nBasename;
	}

	public void setI18nBasename(String i18nBasename) {
		this.i18nBasename = i18nBasename;
	}

	public AppFactoryMetaData i18nBasename(String i18nBasename) {
		setI18nBasename(i18nBasename);
		return this;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public AppFactoryMetaData icon(String icon) {
		setIcon(icon);
		return this;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public AppFactoryMetaData theme(String theme) {
		setTheme(theme);
		return this;
	}
}

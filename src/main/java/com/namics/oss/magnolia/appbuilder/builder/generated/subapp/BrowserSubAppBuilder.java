package com.namics.oss.magnolia.appbuilder.builder.generated.subapp;

import info.magnolia.ui.actionbar.definition.ActionbarDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.app.SubApp;
import info.magnolia.ui.contentapp.browser.ConfiguredBrowserSubAppDescriptor;
import info.magnolia.ui.imageprovider.definition.ImageProviderDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnectorDefinition;
import info.magnolia.ui.workbench.definition.WorkbenchDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.034529"
)
public class BrowserSubAppBuilder extends ConfiguredBrowserSubAppDescriptor {
	public BrowserSubAppBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public BrowserSubAppBuilder closable(boolean closable) {
		this.setClosable(closable);
		return this;
	}

	public BrowserSubAppBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public BrowserSubAppBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public BrowserSubAppBuilder subAppClass(Class<? extends SubApp> subAppClass) {
		this.setSubAppClass(subAppClass);
		return this;
	}

	public BrowserSubAppBuilder actions(Map<String, ActionDefinition> actions) {
		this.setActions(actions);
		return this;
	}

	public BrowserSubAppBuilder actionbar(ActionbarDefinition actionbar) {
		this.setActionbar(actionbar);
		return this;
	}

	public BrowserSubAppBuilder imageProvider(ImageProviderDefinition imageProvider) {
		this.setImageProvider(imageProvider);
		return this;
	}

	public BrowserSubAppBuilder contentConnector(ContentConnectorDefinition contentConnector) {
		this.setContentConnector(contentConnector);
		return this;
	}

	public BrowserSubAppBuilder workbench(WorkbenchDefinition workbench) {
		this.setWorkbench(workbench);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16, String key17, ActionDefinition value17) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		paramMap.put(key17, value17);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16, String key17, ActionDefinition value17, String key18,
			ActionDefinition value18) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		paramMap.put(key17, value17);
		paramMap.put(key18, value18);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16, String key17, ActionDefinition value17, String key18,
			ActionDefinition value18, String key19, ActionDefinition value19) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		paramMap.put(key17, value17);
		paramMap.put(key18, value18);
		paramMap.put(key19, value19);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16, String key17, ActionDefinition value17, String key18,
			ActionDefinition value18, String key19, ActionDefinition value19, String key20,
			ActionDefinition value20) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		paramMap.put(key17, value17);
		paramMap.put(key18, value18);
		paramMap.put(key19, value19);
		paramMap.put(key20, value20);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16, String key17, ActionDefinition value17, String key18,
			ActionDefinition value18, String key19, ActionDefinition value19, String key20,
			ActionDefinition value20, String key21, ActionDefinition value21) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		paramMap.put(key17, value17);
		paramMap.put(key18, value18);
		paramMap.put(key19, value19);
		paramMap.put(key20, value20);
		paramMap.put(key21, value21);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16, String key17, ActionDefinition value17, String key18,
			ActionDefinition value18, String key19, ActionDefinition value19, String key20,
			ActionDefinition value20, String key21, ActionDefinition value21, String key22,
			ActionDefinition value22) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		paramMap.put(key17, value17);
		paramMap.put(key18, value18);
		paramMap.put(key19, value19);
		paramMap.put(key20, value20);
		paramMap.put(key21, value21);
		paramMap.put(key22, value22);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16, String key17, ActionDefinition value17, String key18,
			ActionDefinition value18, String key19, ActionDefinition value19, String key20,
			ActionDefinition value20, String key21, ActionDefinition value21, String key22,
			ActionDefinition value22, String key23, ActionDefinition value23) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		paramMap.put(key17, value17);
		paramMap.put(key18, value18);
		paramMap.put(key19, value19);
		paramMap.put(key20, value20);
		paramMap.put(key21, value21);
		paramMap.put(key22, value22);
		paramMap.put(key23, value23);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16, String key17, ActionDefinition value17, String key18,
			ActionDefinition value18, String key19, ActionDefinition value19, String key20,
			ActionDefinition value20, String key21, ActionDefinition value21, String key22,
			ActionDefinition value22, String key23, ActionDefinition value23, String key24,
			ActionDefinition value24) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		paramMap.put(key17, value17);
		paramMap.put(key18, value18);
		paramMap.put(key19, value19);
		paramMap.put(key20, value20);
		paramMap.put(key21, value21);
		paramMap.put(key22, value22);
		paramMap.put(key23, value23);
		paramMap.put(key24, value24);
		this.setActions(paramMap);
		return this;
	}

	public BrowserSubAppBuilder actions(String key1, ActionDefinition value1, String key2,
			ActionDefinition value2, String key3, ActionDefinition value3, String key4,
			ActionDefinition value4, String key5, ActionDefinition value5, String key6,
			ActionDefinition value6, String key7, ActionDefinition value7, String key8,
			ActionDefinition value8, String key9, ActionDefinition value9, String key10,
			ActionDefinition value10, String key11, ActionDefinition value11, String key12,
			ActionDefinition value12, String key13, ActionDefinition value13, String key14,
			ActionDefinition value14, String key15, ActionDefinition value15, String key16,
			ActionDefinition value16, String key17, ActionDefinition value17, String key18,
			ActionDefinition value18, String key19, ActionDefinition value19, String key20,
			ActionDefinition value20, String key21, ActionDefinition value21, String key22,
			ActionDefinition value22, String key23, ActionDefinition value23, String key24,
			ActionDefinition value24, String key25, ActionDefinition value25) {
		Map<String, ActionDefinition> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		paramMap.put(key10, value10);
		paramMap.put(key11, value11);
		paramMap.put(key12, value12);
		paramMap.put(key13, value13);
		paramMap.put(key14, value14);
		paramMap.put(key15, value15);
		paramMap.put(key16, value16);
		paramMap.put(key17, value17);
		paramMap.put(key18, value18);
		paramMap.put(key19, value19);
		paramMap.put(key20, value20);
		paramMap.put(key21, value21);
		paramMap.put(key22, value22);
		paramMap.put(key23, value23);
		paramMap.put(key24, value24);
		paramMap.put(key25, value25);
		this.setActions(paramMap);
		return this;
	}
}

package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.MarkNodeAsDeletedActionDefinition;
import java.lang.Class;
import java.lang.Object;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.787659"
)
public class MarkNodeAsDeletedActionBuilder extends MarkNodeAsDeletedActionDefinition {
	public MarkNodeAsDeletedActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder command(String command) {
		this.setCommand(command);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder catalog(String catalog) {
		this.setCatalog(catalog);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(Map<String, Object> params) {
		this.setParams(params);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder asynchronous(boolean asynchronous) {
		this.setAsynchronous(asynchronous);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder delay(int delay) {
		this.setDelay(delay);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder parallel(boolean parallel) {
		this.setParallel(parallel);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder timeToWait(int timeToWait) {
		this.setTimeToWait(timeToWait);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder notifyUser(boolean notifyUser) {
		this.setNotifyUser(notifyUser);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(key1, value1);
		paramMap.put(key2, value2);
		paramMap.put(key3, value3);
		paramMap.put(key4, value4);
		paramMap.put(key5, value5);
		paramMap.put(key6, value6);
		paramMap.put(key7, value7);
		paramMap.put(key8, value8);
		paramMap.put(key9, value9);
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16, String key17,
			Object value17) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16, String key17,
			Object value17, String key18, Object value18) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16, String key17,
			Object value17, String key18, Object value18, String key19, Object value19) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16, String key17,
			Object value17, String key18, Object value18, String key19, Object value19, String key20,
			Object value20) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16, String key17,
			Object value17, String key18, Object value18, String key19, Object value19, String key20,
			Object value20, String key21, Object value21) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16, String key17,
			Object value17, String key18, Object value18, String key19, Object value19, String key20,
			Object value20, String key21, Object value21, String key22, Object value22) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16, String key17,
			Object value17, String key18, Object value18, String key19, Object value19, String key20,
			Object value20, String key21, Object value21, String key22, Object value22, String key23,
			Object value23) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16, String key17,
			Object value17, String key18, Object value18, String key19, Object value19, String key20,
			Object value20, String key21, Object value21, String key22, Object value22, String key23,
			Object value23, String key24, Object value24) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}

	public MarkNodeAsDeletedActionBuilder params(String key1, Object value1, String key2,
			Object value2, String key3, Object value3, String key4, Object value4, String key5,
			Object value5, String key6, Object value6, String key7, Object value7, String key8,
			Object value8, String key9, Object value9, String key10, Object value10, String key11,
			Object value11, String key12, Object value12, String key13, Object value13, String key14,
			Object value14, String key15, Object value15, String key16, Object value16, String key17,
			Object value17, String key18, Object value18, String key19, Object value19, String key20,
			Object value20, String key21, Object value21, String key22, Object value22, String key23,
			Object value23, String key24, Object value24, String key25, Object value25) {
		Map<String, Object> paramMap = new HashMap<>();
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
		this.setParams(paramMap);
		return this;
	}
}

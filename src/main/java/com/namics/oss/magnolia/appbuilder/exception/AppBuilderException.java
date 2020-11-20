package com.namics.oss.magnolia.appbuilder.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AppBuilderException extends RuntimeException {
	private static final long serialVersionUID = -229172473220960435L;

	private static final String EMPTY_MESSAGE = "EMPTY MESSAGE";

	private final transient Map<String, Object> data = new HashMap<>();

	/**
	 * Default constructor for AppBuilderException.
	 *
	 * @param message Custom exception message
	 * @param params  Message parameters
	 */
	public AppBuilderException(String message, String... params) {
		super(MessageFormatter.arrayFormat(message, params).getMessage());
	}

	private AppBuilderException(Throwable e) {
		super(e.getMessage(), e);
	}

	/**
	 * @param exception the existing exception
	 * @return AppBuilderException wrapped around the original exception with default error code
	 */
	public static AppBuilderException wrap(Throwable exception) {
		if (exception instanceof AppBuilderException) {
			return (AppBuilderException) exception;
		} else {
			return new AppBuilderException(exception);
		}
	}

	/**
	 * @param key   of the value to add to exception
	 * @param value to add to exception
	 * @return The exception itself
	 */
	public AppBuilderException set(final String key, final Object value) {
		data.put(key, value);
		return this;
	}

	@Override
	public String getMessage() {
		final StringBuilder message = new StringBuilder(StringUtils.defaultString(super.getMessage(), EMPTY_MESSAGE));
		if (!data.isEmpty()) {
			message.append(System.lineSeparator());
			message.append(data.entrySet()
					.stream()
					.map(entry -> entry.getKey() + ": " + entry.getValue())
					.collect(Collectors.joining(", ")));
		}
		return message.toString();
	}
}

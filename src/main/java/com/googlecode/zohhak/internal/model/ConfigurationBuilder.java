package com.googlecode.zohhak.internal.model;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.zohhak.api.ConfigurationDefinition;



public class ConfigurationBuilder {

	private List<Class<?>> coercers = new ArrayList<Class<?>>(2);
	private String separator = ConfigurationDefinition.DEFAULT_SEPARATOR;
	private String stringBoundary = ConfigurationDefinition.DEFAULT_STRING_BOUNDARY;
	
	public void addConfigLine(ConfigLine configLine) {
		if (!configLine.isInheritCoercers()) {
			coercers.clear();
		}
		addCoercers(configLine.getCoercers());
		overrideSeparator(configLine.getSeparator());
		overrideStringBoundary(configLine.getStringBoundary());
	}
	
	public List<Class<?>> getCoercers() {
		return coercers;
	}

	public String getSeparator() {
		return separator;
	}

	public String getStringBoundary() {
		return stringBoundary;
	}
	
	private void overrideSeparator(String separator) {
		if (!ConfigurationDefinition.INHERIT.equals(separator)) {
			this.separator = separator;
		}
	}
	
	private void overrideStringBoundary(String stringBoundary) {
		if (!ConfigurationDefinition.INHERIT.equals(stringBoundary)) {
			this.stringBoundary = stringBoundary;
		}
	}
	
	private final void addCoercers(Class<?>... additionalCoercers) {
		coercers.addAll(asList(additionalCoercers));
	}

}

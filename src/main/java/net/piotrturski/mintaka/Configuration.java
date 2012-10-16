package net.piotrturski.mintaka;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;


public class Configuration {

	private List<Class<?>> coercers = new ArrayList<Class<?>>(2);
	private String separator = ConfigurationDefinition.DEFAULT_SEPARATOR;
	private String stringBoundary = ConfigurationDefinition.DEFAULT_STRING_BOUNDARY;
	
	public Configuration() {
		coercers.add(defaultCoercer());
	}
	
	public final void addCoercers(Class<?>[] additionalCoercers) {
		coercers.addAll(asList(additionalCoercers));
	}
	
	protected Class<?> defaultCoercer() {
		return DefaultCoercer.class;
	}

	public List<Class<?>> getCoercers() {
		return coercers;
	}

	public String getSeparator() {
		return separator;
	}

	public void overrideSeparator(String separator) {
		if (!ConfigurationDefinition.DEFAULT_SEPARATOR_MARKER.equals(separator)) {
			this.separator = separator;
		}
	}
	
	public void overrideStringBoundary(String stringBoundary) {
		if (!ConfigurationDefinition.DEFAULT_STRING_BOUNDARY_MARKER.equals(stringBoundary)) {
			this.stringBoundary = stringBoundary;
		}
	}
	
	public String getStringBoundary() {
		return stringBoundary;
	}

}

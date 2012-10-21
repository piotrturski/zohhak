package net.piotrturski.mintaka;


public class Configuration {

	private static final Class<?>[] EMPTY_COERCERS = new Class<?>[] {};

	public boolean inheritCoercers() {
		return true;
	};
	
	public Class<?>[] coercer() {
		return EMPTY_COERCERS;
	}
	
	public String separator() {
		return ConfigurationDefinition.DEFAULT_SEPARATOR_MARKER;
	}
	
	public String stringBoundary() {
		return ConfigurationDefinition.DEFAULT_SEPARATOR_MARKER;
	}
	
	protected final Class<?>[] asArray(Class<?>... coercers) {
		return coercers;
	}
	
}

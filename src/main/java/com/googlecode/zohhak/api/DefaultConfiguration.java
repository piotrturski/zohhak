package com.googlecode.zohhak.api;

public class DefaultConfiguration implements Configuration {

	private static final Class<?>[] DEFAULT_COERCERS = new Class<?>[] {DefaultCoercer.class}; 
	
	public boolean inheritCoercers() {
		return false;
	}
	
	public Class<?>[] coercers() {
		return DEFAULT_COERCERS;
	}
	
	public String separator() {
		return ConfigurationDefinition.DEFAULT_SEPARATOR;
	}
	
	public String stringBoundary() {
		return ConfigurationDefinition.DEFAULT_STRING_BOUNDARY;
	}
	
	protected final Class<?>[] asArray(Class<?>... coercers) {
		return coercers;
	}
}

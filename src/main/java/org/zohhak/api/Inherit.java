package org.zohhak.api;

public class Inherit implements Configuration {

	private static final Class<?>[] EMPTY_COERCERS = new Class<?>[] {};
	
	@Override
	public boolean inheritCoercers() {
		return true;
	}

	@Override
	public Class<?>[] coercer() {
		return EMPTY_COERCERS;
	}

	@Override
	public String separator() {
		return ConfigurationDefinition.INHERIT;
	}

	@Override
	public String stringBoundary() {
		return ConfigurationDefinition.INHERIT;
	}

}

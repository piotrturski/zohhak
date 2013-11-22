package com.googlecode.zohhak.api;

public class Inherit implements Configuration {

	private static final Class<?>[] EMPTY_COERCERS = new Class<?>[] {};
	
	public boolean inheritCoercers() {
		return true;
	}

	public Class<?>[] coercers() {
		return EMPTY_COERCERS;
	}

	public String separator() {
		return ConfigurationDefinition.INHERIT;
	}

	public String stringBoundary() {
		return ConfigurationDefinition.INHERIT;
	}

}

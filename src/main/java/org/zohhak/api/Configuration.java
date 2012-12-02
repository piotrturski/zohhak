package org.zohhak.api;

public interface Configuration {

	boolean inheritCoercers();
	
	Class<?>[] coercer();
	
	String separator();
	
	String stringBoundary();
	
}

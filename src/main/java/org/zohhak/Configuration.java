package org.zohhak;

public interface Configuration {

	boolean inheritCoercers();
	
	Class<?>[] coercer();
	
	String separator();
	
	String stringBoundary();
	
}

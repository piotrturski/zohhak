package com.googlecode.zohhak.api;

public interface Configuration {

	boolean inheritCoercers();
	
	Class<?>[] coercers();
	
	String separator();
	
	String stringBoundary();
	
}

package net.piotrturski.mintaka;

public interface Configuration {

	boolean inheritCoercers();
	
	Class<?>[] coercer();
	
	String separator();
	
	String stringBoundary();
	
}

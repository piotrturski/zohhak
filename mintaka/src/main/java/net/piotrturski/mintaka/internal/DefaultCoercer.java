package net.piotrturski.mintaka.internal;

public class DefaultCoercer {

	public String toString(String input) {
		return input;
	}
	
	public Integer toInteger(String input) {
		return Integer.parseInt(input);
	}
	
	public long toLong(String input) {
		return Long.parseLong(input);
	}
}

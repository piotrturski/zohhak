package net.piotrturski.mintaka.internal.coercing;

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
	
	public char toChar(String input) {
		return input.charAt(0);
	}
	
	public byte toByte(String input) {
		return Byte.parseByte(input);
	}
	
	public short toShort(String input) {
		return Short.parseShort(input);
	}
	
	public float toFloat(String input) {
		return Float.parseFloat(input);
	}
	
	public double toDouble(String input) {
		return Double.parseDouble(input);
	}
	
	public boolean toBoolean(String input) {
		Boolean parsed = Boolean.valueOf(input);
		
		if (parsed.toString().equalsIgnoreCase(input)) {
			return parsed;
		}
		throw new IllegalArgumentException(input);
	}
}

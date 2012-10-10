package net.piotrturski.mintaka.internal.model;

import java.lang.reflect.Type;

public class ParametersPair {

	public final Type targetType;
	public final String stringToParse;

	public ParametersPair(Type targetType, String stringToParse) {
		this.targetType = targetType;
		this.stringToParse = stringToParse;
	}
}

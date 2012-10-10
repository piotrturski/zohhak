package net.piotrturski.mintaka.internal.model;

import java.lang.reflect.Type;
import java.util.Iterator;

final class ParametersIterator implements Iterator<ParametersPair> {
	private final Type[] genericParameterTypes;
	private final String[] splitedParameters;
	private int position = -1;

	public ParametersIterator(Type[] genericParameterTypes, String[] splitedParameters) {
		this.genericParameterTypes = genericParameterTypes;
		this.splitedParameters = splitedParameters;

	}

	@Override
	public ParametersPair next() {
		position++;
		return new ParametersPair(genericParameterTypes[position], splitedParameters[position]);
	}

	@Override
	public boolean hasNext() {
		return position < genericParameterTypes.length;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
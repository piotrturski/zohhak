package net.piotrturski.mintaka.internal.model;

import java.util.Iterator;

abstract public class DoubleArrayIterator<T, V1, V2> implements Iterator<T> {

	private final V1[] array1;
	private final V2[] array2;
	private int position = 0;

	protected DoubleArrayIterator(V1[] array1, V2[] array2) {
		if (array1.length != array2.length) {
			throw new IllegalArgumentException("arays must have equal lengths");
		}
		this.array1 = array1;
		this.array2 = array2;
	}
	
	abstract protected T fabricate(V1 value1, V2 value2); 
	
	@Override
	public boolean hasNext() {
		return position < array1.length;
	}

	@Override
	public T next() {
		T nextElement = fabricate(array1[position], array2[position]);
		position++;
		return nextElement;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}

}

package org.zohhak.internal.model;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.zohhak.TestWith;


public class SingleTestMethod {

	public ConfigurationBuilder configuration;
	public final String parametersLine;
	public final Method realMethod;
	public final TestWith annotation;
	private final Type[] genericParameterTypes;

	public SingleTestMethod(Method realMethod, int lineIndex) {
		this.realMethod = realMethod;
		annotation = realMethod.getAnnotation(TestWith.class);
		parametersLine = annotation.value()[lineIndex];
		genericParameterTypes = realMethod.getGenericParameterTypes();
	}

	public Type getParameterType(int nr) {
		return genericParameterTypes[nr];
	}
	
	public int getArity() {
		return genericParameterTypes.length;
	}
	
}

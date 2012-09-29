package net.piotrturski.mintaka.internal;

import java.lang.reflect.Method;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.TestWith;

public class SingleTestMethod {

	Configuration configuration;
	public final String parametersLine;
	String[] splitedParameters;
	public final Method realMethod;
	private final Executor executor;
	public final TestWith annotation;
	
	public SingleTestMethod(Method realMethod, int lineIndex, Executor executor) {
		this.realMethod = realMethod;
		this.executor = executor;
		annotation = realMethod.getAnnotation(TestWith.class);
		parametersLine = annotation.value()[lineIndex];
		
	}

	public Object[] calculateParameters() {
		return executor.calculateParameters(this, parametersLine);
	}
	
}

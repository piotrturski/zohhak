package net.piotrturski.mintaka.internal.model;

import java.lang.reflect.Method;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.TestWith;

public class SingleTestMethod {

	public Configuration configuration;
	public final String parametersLine;
	public String[] splitedParameters;
	public final Method realMethod;
	public final TestWith annotation;
	
	public SingleTestMethod(Method realMethod, int lineIndex) {
		this.realMethod = realMethod;
		annotation = realMethod.getAnnotation(TestWith.class);
		parametersLine = annotation.value()[lineIndex];
		
	}

}

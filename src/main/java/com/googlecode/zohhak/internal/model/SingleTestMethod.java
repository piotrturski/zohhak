package com.googlecode.zohhak.internal.model;

import java.lang.reflect.Method;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.backend.ConfigurationBuilder;


public class SingleTestMethod {

	public ConfigurationBuilder configuration;
	public final String parametersLine;
	public final Method realMethod;
	public final TestWith annotation;

	public SingleTestMethod(Method realMethod, int lineIndex) {
		this.realMethod = realMethod;
		annotation = realMethod.getAnnotation(TestWith.class);
		parametersLine = annotation.value()[lineIndex];
	}
}

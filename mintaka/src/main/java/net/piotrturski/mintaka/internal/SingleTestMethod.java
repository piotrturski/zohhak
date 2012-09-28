package net.piotrturski.mintaka.internal;

import java.lang.reflect.Method;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.TestWith;

public class SingleTestMethod {

	Configuration configuration;
	private final int lineIndex;
	public final String parametersLine;
	private String[] splitedParameters;
	public final Method realMethod;
	private final Executor executor;
	
	public SingleTestMethod(Method realMethod, int lineIndex, Executor executor) {
		this.realMethod = realMethod;
		this.lineIndex = lineIndex;
		this.executor = executor;
		parametersLine = realMethod.getAnnotation(TestWith.class).value()[lineIndex];
	}

	public Object[] calculateParameters() {
		return executor.calculateParameters(this, parametersLine);
	}
	
}

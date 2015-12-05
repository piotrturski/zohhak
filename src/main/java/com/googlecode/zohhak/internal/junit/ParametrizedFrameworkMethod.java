package com.googlecode.zohhak.internal.junit;


import org.junit.runners.model.FrameworkMethod;

import com.googlecode.zohhak.internal.Executor;
import com.googlecode.zohhak.internal.model.SingleTestMethod;

public class ParametrizedFrameworkMethod extends FrameworkMethod {

	private final SingleTestMethod singleTestMethod;
	private final Executor executor;

	public ParametrizedFrameworkMethod(SingleTestMethod singleTestMethod, Executor executor) {
		super(singleTestMethod.realMethod);
		this.singleTestMethod = singleTestMethod;
		this.executor = executor;
	}

	@Override
	public Object invokeExplosively(Object target, Object... params) throws Throwable {
		Object[] parameters = executor.calculateParameters(singleTestMethod, singleTestMethod.parametersLine);
		return super.invokeExplosively(target, parameters);
	}

	public String getParametersLine() {
		return singleTestMethod.parametersLine;
	}
	
}

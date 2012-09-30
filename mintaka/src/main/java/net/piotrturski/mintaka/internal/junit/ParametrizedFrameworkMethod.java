package net.piotrturski.mintaka.internal.junit;

import net.piotrturski.mintaka.internal.SingleTestMethod;

import org.junit.runners.model.FrameworkMethod;

public class ParametrizedFrameworkMethod extends FrameworkMethod {

	private final SingleTestMethod singleTestMethod;

	public ParametrizedFrameworkMethod(SingleTestMethod singleTestMethod) {
		super(singleTestMethod.realMethod);
		this.singleTestMethod = singleTestMethod;
	}

	@Override
	public Object invokeExplosively(Object target, Object... params) throws Throwable {
		Object[] parameters = singleTestMethod.calculateParameters();
		return super.invokeExplosively(target, parameters);
	}

	public String getParametersLine() {
		return singleTestMethod.parametersLine;
	}
	
}

package net.piotrturski.mintaka;

import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class MethodAdder {

	public List<FrameworkMethod> addParametrizedMethods(TestClass testClass) {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> parametrizedMethods = testClass.getAnnotatedMethods(TestWith.class);
		for (FrameworkMethod parametrizedMethod : parametrizedMethods) {
			ParametrizedFrameworkMethod parametrizedFrameworkMethod = new ParametrizedFrameworkMethod(parametrizedMethod.getMethod(), 0);
			result.add(parametrizedFrameworkMethod);
		}
		return result;
	}
	
}

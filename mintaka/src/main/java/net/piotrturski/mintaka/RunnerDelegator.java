package net.piotrturski.mintaka;

import java.util.ArrayList;
import java.util.List;

import net.piotrturski.mintaka.internal.junit.ParametrizedFrameworkMethod;

import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class RunnerDelegator {

	public List<FrameworkMethod> computeTestMethods(TestClass testClass, List<FrameworkMethod> testMethodsFromSuperclass) {
		List<FrameworkMethod> annotatedMethods = addParametrizedMethods(testClass);
		annotatedMethods.addAll(testMethodsFromSuperclass);
		return annotatedMethods;
	}
	
	public Description describeChild(TestClass testClass, Description descriptionFromSuperclass, FrameworkMethod method) {
		TestWith testWithAnnotation = method.getAnnotation(TestWith.class);
		if (testWithAnnotation == null) {
			return descriptionFromSuperclass;
		}
		return Description.createTestDescription(testClass.getClass(), ((ParametrizedFrameworkMethod) method).getDescriptionName(), method.getAnnotations());
	}
	
	private List<FrameworkMethod> addParametrizedMethods(TestClass testClass) {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> parametrizedMethods = testClass.getAnnotatedMethods(TestWith.class);
		for (FrameworkMethod parametrizedMethod : parametrizedMethods) {
			TestWith testWithAnnotation = parametrizedMethod.getAnnotation(TestWith.class);
			for (int i = 0; i < testWithAnnotation.value().length; i++) {
				ParametrizedFrameworkMethod parametrizedFrameworkMethod = new ParametrizedFrameworkMethod(parametrizedMethod.getMethod(), i);
				result.add(parametrizedFrameworkMethod);
			}
		}
		return result;
	}
	
}

package net.piotrturski.mintaka;

import java.util.ArrayList;
import java.util.List;

import net.piotrturski.mintaka.internal.junit.ParametrizedFrameworkMethod;

import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class RunnerDelegator {

	/**
	 * returns N methods for each parameterized method 
	 * @param testClass
	 * @param testMethodsFromSuperclass
	 * @return
	 */
	public List<FrameworkMethod> computeTestMethods(TestClass testClass, List<FrameworkMethod> testMethodsFromSuperclass) {
		ArrayList<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
//		List<FrameworkMethod> annotatedMethods = testClass.getAnnotatedMethods(TestWith.class);
		List<FrameworkMethod> annotatedMethods = addParametrizedMethods(testClass);
		//annotatedMethods.addAll(addParametrizedMethods(testClass));
		result.addAll(annotatedMethods);
		result.addAll(testMethodsFromSuperclass);
		return result;
	}
	
	public Description describeChild(TestClass testClass, Description descriptionFromSuperclass, FrameworkMethod method) {
		TestWith testWithAnnotation = method.getAnnotation(TestWith.class);
		if (testWithAnnotation == null) {
			return descriptionFromSuperclass;
		}
		Description description = Description.createSuiteDescription(method.getName());
		//Description description = Description.createTestDescription(Integer.class, "methodWithParam");
		//Description childDescription = Description.createTestDescription(testClass.getJavaClass(), "methodWithParam[1]");
		//description.addChild(childDescription);
		addChildrenDescription(description, method, testClass.getJavaClass());
		return description;
		//descriptionFromSuperclass.addChild(description);
		//return Description.createTestDescription(testClass.getJavaClass(), ((ParametrizedFrameworkMethod) method).getDescriptionName(), method.getAnnotations());
	}
	
	private void addChildrenDescription(Description parent, FrameworkMethod method, Class<?> testClass) {
		String[] values = method.getAnnotation(TestWith.class).value();
		for (int i = 0; i < values.length; i++) {
			Description childDescription = Description.createTestDescription(testClass, method.getName()+" ["+values[i]+"]");
			parent.addChild(childDescription);
		}
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

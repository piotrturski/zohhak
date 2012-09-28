package net.piotrturski.mintaka.internal;

import java.util.ArrayList;
import java.util.List;

import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.internal.junit.ParametrizedFrameworkMethod;

import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class RunnerDelegator {

	static List<FrameworkMethod> getParametrizedLeafMethods(TestClass testClass) {
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

	static public List<FrameworkMethod> computeAllTestMethods(TestClass testClass2, List<FrameworkMethod> list) {
		ArrayList<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> annotatedMethods = getParametrizedLeafMethods(testClass2);
		result.addAll(annotatedMethods);
		result.addAll(list);
		return result;
	}

	static public boolean isParameterized(FrameworkMethod method) {
		return method.getAnnotation(TestWith.class) != null;
	}

	static public Description describeSingleInvocationOfParametrizedMethod(FrameworkMethod method, TestClass testClass) {
		ParametrizedFrameworkMethod parametrizedMethod = (ParametrizedFrameworkMethod) method;
		return RunnerDelegator.prepareDescription(testClass.getJavaClass(), method.getName(), parametrizedMethod.getParametersLine());
	}

	static public Description describeParametrizedMethodWithChildren(FrameworkMethod method, TestClass testClass) {
		// Description parent = Description.createTestDescription(testClass, method.getName());
		Description parent = RunnerDelegator.describeParentParametrizedMethod(method);
		String[] parameters = method.getAnnotation(TestWith.class).value();
		for (String parametersLine : parameters) {
			Description childDescription = RunnerDelegator.prepareDescription(testClass.getJavaClass(), method.getName(), parametersLine);
			parent.addChild(childDescription);
		}
		return parent;
	}

	static public List<FrameworkMethod> getClassChildrenToDescribe(TestClass testClass, List<FrameworkMethod> standardTestMethods) {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		result.addAll(standardTestMethods);
		List<FrameworkMethod> annotatedMethods = testClass.getAnnotatedMethods(TestWith.class);
		result.addAll(annotatedMethods);
		return result;
	}

	static Description describeParentParametrizedMethod(FrameworkMethod method) {
		// return Description.createSuiteDescription(method.getName()+ "("+testClass.getCanonicalName()+")");
		return Description.createSuiteDescription(method.getName());
	}

	static Description prepareDescription(Class<?> testClass, String methodName, String parametersLine) {
		return Description.createTestDescription(testClass, methodName + " [" + parametersLine + "]");
	}

}

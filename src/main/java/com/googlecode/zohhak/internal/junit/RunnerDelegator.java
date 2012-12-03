package com.googlecode.zohhak.internal.junit;

import java.util.ArrayList;
import java.util.List;


import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.internal.Executor;
import com.googlecode.zohhak.internal.IoCContainer;
import com.googlecode.zohhak.internal.model.SingleTestMethod;

public class RunnerDelegator {

	IoCContainer ioCContainer = new IoCContainer();
	Executor executor = ioCContainer.getExecutor();
	
	protected List<FrameworkMethod> getParametrizedLeafMethods(TestClass testClass) {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> parametrizedMethods = getAnnotatedMethods(testClass);
		for (FrameworkMethod parametrizedMethod : parametrizedMethods) {
			addInvocationsOfSingleParameterizedMethod(result, parametrizedMethod);
		}
		return result;
	}

	static private List<FrameworkMethod> getAnnotatedMethods(TestClass testClass) {
		return testClass.getAnnotatedMethods(TestWith.class); //TODO possible indirect invocation
	}

	private void addInvocationsOfSingleParameterizedMethod(List<FrameworkMethod> result, FrameworkMethod parametrizedMethod) {
		String[] parameters = getParameters(parametrizedMethod);
		int numberOfInvocations = parameters.length;
		for (int i = 0; i < numberOfInvocations; i++) {
			SingleTestMethod singleTestMethod = new SingleTestMethod(parametrizedMethod.getMethod(), i);
			ParametrizedFrameworkMethod parametrizedFrameworkMethod = new ParametrizedFrameworkMethod(singleTestMethod, executor);
			result.add(parametrizedFrameworkMethod);
		}
	}

	public List<FrameworkMethod> computeAllTestMethods(TestClass testClass, List<FrameworkMethod> list) {
		ArrayList<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> annotatedMethods = getParametrizedLeafMethods(testClass);
		result.addAll(annotatedMethods);
		result.addAll(list);
		return result;
	}

	public static boolean isParameterized(FrameworkMethod method) {
		return method.getMethod().isAnnotationPresent(TestWith.class);
	}

	public static Description describeSingleInvocationOfParametrizedMethod(FrameworkMethod method, TestClass testClass) {
		ParametrizedFrameworkMethod parametrizedMethod = (ParametrizedFrameworkMethod) method;
		return RunnerDelegator.prepareDescription(testClass.getJavaClass(), method.getName(), parametrizedMethod.getParametersLine());
	}

	public static Description describeParametrizedMethodWithChildren(FrameworkMethod method, TestClass testClass) {
		// Description parent = Description.createTestDescription(testClass, method.getName());
		Description parent = RunnerDelegator.describeParentParametrizedMethod(method);
		String[] parameters = getParameters(method);
		for (String parametersLine : parameters) {
			Description childDescription = RunnerDelegator.prepareDescription(testClass.getJavaClass(), method.getName(), parametersLine);
			parent.addChild(childDescription);
		}
		return parent;
	}

	private static String[] getParameters(FrameworkMethod method) {
		return method.getAnnotation(TestWith.class).value();
	}

	public static List<FrameworkMethod> getClassChildrenToDescribe(TestClass testClass, List<FrameworkMethod> standardTestMethods) {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		result.addAll(standardTestMethods);
		List<FrameworkMethod> annotatedMethods = getAnnotatedMethods(testClass);
		result.addAll(annotatedMethods);
		return result;
	}

	static Description describeParentParametrizedMethod(FrameworkMethod method) {
		// return Description.createSuiteDescription(method.getName()+ "("+testClass.getCanonicalName()+")");
		return Description.createSuiteDescription(method.getName());
	}

	static Description prepareDescription(Class<?> testClass, String methodName, String parametersLine) {
		//TODO test what if parameter contains '('
		return Description.createTestDescription(testClass, methodName + " [" + parametersLine + "]");
	}

}

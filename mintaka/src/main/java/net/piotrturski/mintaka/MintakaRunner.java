package net.piotrturski.mintaka;

import java.util.ArrayList;
import java.util.List;

import net.piotrturski.mintaka.internal.junit.ParametrizedFrameworkMethod;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class MintakaRunner extends BlockJUnit4ClassRunner {

	private Description description;
	private Class<?> testClass;

	public MintakaRunner(Class<?> klass) throws InitializationError {
		super(klass);
		testClass = klass;
	}

	/**
	 * all methods that should be run. only leaves
	 */
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		ArrayList<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> annotatedMethods = getParametrizedLeafMethods();
		result.addAll(annotatedMethods);
		result.addAll(super.computeTestMethods());
		return result;
	}

	private List<FrameworkMethod> getParametrizedLeafMethods() {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> parametrizedMethods = getTestClass().getAnnotatedMethods(TestWith.class);
		for (FrameworkMethod parametrizedMethod : parametrizedMethods) {
			TestWith testWithAnnotation = parametrizedMethod.getAnnotation(TestWith.class);
			for (int i = 0; i < testWithAnnotation.value().length; i++) {
				ParametrizedFrameworkMethod parametrizedFrameworkMethod = new ParametrizedFrameworkMethod(parametrizedMethod.getMethod(), i, getTestClass().getJavaClass());
				result.add(parametrizedFrameworkMethod);
			}
		}
		return result;
	}
	
	@Override
	public void filter(Filter filter) throws NoTestsRemainException {
		OrParentFilter orParentFilter = OrParentFilter.decorate(filter);
		super.filter(orParentFilter);
	}
	
	@Override
	public Description getDescription() {
		if (description != null) return description;
		description = Description.createSuiteDescription(getName(), getRunnerAnnotations());
		for (FrameworkMethod child : getClassChildrenToDescribe())
			description.addChild(describeMethod(child));
		return description;
	}

	private Description describeMethod(FrameworkMethod method) {
		if (method.getAnnotation(TestWith.class) != null) {
			return describeParametrizedWithChildren(method);
		} else {
			return super.describeChild(method);
		}
	}

	private Description describeParametrizedWithChildren(FrameworkMethod method) {
//		Description parent = Description.createTestDescription(testClass, method.getName());
		Description parent = describeParentParametrizedMethod(method);
		String[] parameters = method.getAnnotation(TestWith.class).value();
		for (String parametersLine : parameters) {
			Description childDescription = prepareDescription(testClass, method.getName(), parametersLine);
			parent.addChild(childDescription);
		}
		return parent;
	}
	
	@Override
	protected Description describeChild(FrameworkMethod method) {
		TestWith testWithAnnotation = method.getAnnotation(TestWith.class);
		if (testWithAnnotation == null) {
			return super.describeChild(method);
		}
		Description description = describeSingleInvocationOfParametrizedMethod(method);
		return description;
	}

	private List<FrameworkMethod> getClassChildrenToDescribe() {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> children = super.computeTestMethods();
		result.addAll(children);
		List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(TestWith.class);
		result.addAll(annotatedMethods);
		return result;
	}

	private Description describeParentParametrizedMethod(FrameworkMethod method) {
		//return Description.createSuiteDescription(method.getName()+ "("+testClass.getCanonicalName()+")");
		return Description.createSuiteDescription(method.getName());
	}
	
	private Description describeSingleInvocationOfParametrizedMethod(FrameworkMethod method) {
		ParametrizedFrameworkMethod parametrizedMethod = (ParametrizedFrameworkMethod) method;
		return prepareDescription(testClass, method.getName(), parametrizedMethod.getParametersLine());
	}
	
	private Description prepareDescription(Class<?> testClass, String methodName, String parametersLine) {
		return Description.createTestDescription(testClass, methodName + " [" + parametersLine + "]");
	}

}

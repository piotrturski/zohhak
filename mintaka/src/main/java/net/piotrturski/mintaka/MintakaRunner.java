package net.piotrturski.mintaka;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.piotrturski.mintaka.internal.junit.ParametrizedFrameworkMethod;

import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

public class MintakaRunner extends BlockJUnit4ClassRunner {

	private Description description;
	private Class<?> testClass;
	private ArrayList<FrameworkMethod> fFilteredChildren;

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
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		Description description= describeChild(method);
		if (method.getAnnotation(Ignore.class) != null) {
			notifier.fireTestIgnored(description);
		} else {
			super.runChild(method, notifier);
		}
	}
	
	@Override
	public void filter(final Filter filter) throws NoTestsRemainException {
		Filter filter2 = new Filter() {
			
			@Override
			public boolean shouldRun(Description description) {
				if (filter.shouldRun(description)) {
					return true;
				}
				return filter.shouldRun(cutParamInfo(description));
			}
			
			@Override
			public String describe() {
				return null;
			}
		};
		
		super.filter(filter2);
	}
	
	@Override
	public Description getDescription() {
		if (description != null) return description;
		description = Description.createSuiteDescription(getName(), getRunnerAnnotations());
		for (FrameworkMethod child : getClassChildrenToDescribe())
			description.addChild(describeMethod(child));
		return description;
	}

	private Description cutParamInfo(Description description) {
		String changed = description.getDisplayName().replaceAll(" \\[.*\\]", "");
		return Description.createSuiteDescription(changed);
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

	private void addChildrenDescription(Description parent, FrameworkMethod method, Class<?> testClass) {
		String[] values = method.getAnnotation(TestWith.class).value();
		for (int i = 0; i < values.length; i++) {
			Description childDescription = describeSingleInvocationOfParametrizedMethod(method);
			parent.addChild(childDescription);
		}
	}


}

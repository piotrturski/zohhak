package net.piotrturski.mintaka;

import java.util.ArrayList;
import java.util.List;

import javassist.expr.Instanceof;

import net.piotrturski.mintaka.internal.junit.ParametrizedFrameworkMethod;

import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

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
		// List<FrameworkMethod> annotatedMethods = testClass.getAnnotatedMethods(TestWith.class);
		List<FrameworkMethod> annotatedMethods = getParametrizedLeafMethods(getTestClass());
		// annotatedMethods.addAll(addParametrizedMethods(testClass));
		result.addAll(annotatedMethods);
		result.addAll(super.computeTestMethods());
		return result;
	}

	private List<FrameworkMethod> getParametrizedLeafMethods(TestClass testClass) {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> parametrizedMethods = testClass.getAnnotatedMethods(TestWith.class);
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
			if (method instanceof ParametrizedFrameworkMethod) {
				super.runChild(method, notifier);
				
				
			} else {
			
				super.runChild(method, notifier);
			}
		}
		
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
		Description parent = Description.createSuiteDescription(method.getName());
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
		
		//Description description = Description.createSuiteDescription(method.getName());
		// Description description = Description.createTestDescription(Integer.class, "methodWithParam");
		// Description childDescription = Description.createTestDescription(testClass.getJavaClass(), "methodWithParam[1]");
		// description.addChild(childDescription);
		//addChildrenDescription(description, method, getTestClass().getJavaClass());
		Description description = describeSingleInvocationOfParametrizedMethod(method);
		return description;
		// descriptionFromSuperclass.addChild(description);
		// return Description.createTestDescription(testClass.getJavaClass(), ((ParametrizedFrameworkMethod) method).getDescriptionName(),
		// method.getAnnotations());
	}
	

	private List<FrameworkMethod> getClassChildrenToDescribe() {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> children = super.computeTestMethods();
		result.addAll(children);
		List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(TestWith.class);
		result.addAll(annotatedMethods);
		return result;
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

package org.zohhak.api.runners;

import static org.zohhak.internal.junit.RunnerDelegator.*;

import java.util.List;


import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.zohhak.internal.junit.OrParentFilter;
import org.zohhak.internal.junit.RunnerDelegator;

/**
 * Compatible with JUnit 4.5 - 4.10
 * 
 *
 */
public class ZohhakRunner extends BlockJUnit4ClassRunner {

	protected List<FrameworkMethod> cachedMethods;
	
	public ZohhakRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * all methods that should be run. only leaves
	 */
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		if (cachedMethods == null) {
			cachedMethods = new RunnerDelegator().computeAllTestMethods(getTestClass(), super.computeTestMethods());
		}
		return cachedMethods;
	}

	@Override
	public void filter(Filter filter) throws NoTestsRemainException {
		super.filter(OrParentFilter.decorate(filter));
	}

	@Override
	public Description getDescription() {
		Description description = Description.createSuiteDescription(getTestClass().getName(), getRunnerAnnotations());
		for (FrameworkMethod child : getClassChildrenToDescribe(getTestClass(), super.computeTestMethods())) {
			description.addChild(describeMethod(child));
		}
		return description;
	}

	@Override
	protected Description describeChild(FrameworkMethod method) {
		return isParameterized(method) ? describeSingleInvocationOfParametrizedMethod(method, getTestClass()) : super.describeChild(method);
	}

	private Description describeMethod(FrameworkMethod method) {
		return isParameterized(method) ? describeParametrizedMethodWithChildren(method, getTestClass()) : super.describeChild(method);
	}
}
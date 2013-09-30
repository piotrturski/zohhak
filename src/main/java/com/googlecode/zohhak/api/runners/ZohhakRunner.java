package com.googlecode.zohhak.api.runners;

import static com.googlecode.zohhak.internal.junit.RunnerDelegator.*;

import java.lang.annotation.Annotation;
import java.util.List;


import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.googlecode.zohhak.internal.junit.OrParentFilter;
import com.googlecode.zohhak.internal.junit.RunnerDelegator;

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

	/**
	 * ParrentRunner.getRunnerAnnotations() was introduced in junit 4.10
	 * for earlier versions, this method must be manually defined
	 * 
	 * @since 1.0.1
	 */
	protected Annotation[] getRunnerAnnotations() {
		return getTestClass().getAnnotations();
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
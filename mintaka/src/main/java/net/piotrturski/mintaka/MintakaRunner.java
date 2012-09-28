package net.piotrturski.mintaka;

import static net.piotrturski.mintaka.internal.RunnerDelegator.*;

import java.util.List;

import net.piotrturski.mintaka.internal.OrParentFilter;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class MintakaRunner extends BlockJUnit4ClassRunner {

	public MintakaRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * all methods that should be run. only leaves
	 */
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		return computeAllTestMethods(getTestClass(), super.computeTestMethods());
	}

	@Override
	public void filter(Filter filter) throws NoTestsRemainException {
		super.filter(OrParentFilter.decorate(filter));
	}

	@Override
	public Description getDescription() {
		Description description = Description.createSuiteDescription(getTestClass().getName(), getRunnerAnnotations());
		for (FrameworkMethod child : getClassChildrenToDescribe(getTestClass(), super.computeTestMethods()))
			description.addChild(describeMethod(child));
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
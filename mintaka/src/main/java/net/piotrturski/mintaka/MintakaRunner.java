package net.piotrturski.mintaka;

import java.util.ArrayList;
import java.util.List;


import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class MintakaRunner extends BlockJUnit4ClassRunner {

	public MintakaRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}
	
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		return new RunnerDelegator().computeTestMethods(getTestClass(), super.computeTestMethods());
	}

	@Override
	protected Description describeChild(FrameworkMethod method) {
		return new RunnerDelegator().describeChild(getTestClass(), super.describeChild(method), method);
	}
	
	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		Description description= describeChild(method);
		if (method.getAnnotation(Ignore.class) != null) {
			notifier.fireTestIgnored(description);
		} else {
			runLeaf(methodBlock(method), description, notifier);
		}
	}
	
	@Override
	public Description getDescription() {
		Description description= Description.createSuiteDescription(getName(),
				getRunnerAnnotations());
		for (FrameworkMethod child : getChildrenToDescribe())
			description.addChild(describeChild(child));
		return description;
	}

	private List<FrameworkMethod> getChildrenToDescribe() {
		List<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
		List<FrameworkMethod> children = super.computeTestMethods();
		result.addAll(children);
		List<FrameworkMethod> annotatedMethods = getTestClass().getAnnotatedMethods(TestWith.class);
		result.addAll(annotatedMethods);
		return result;
	}
	
}

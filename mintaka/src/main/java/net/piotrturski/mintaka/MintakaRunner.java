package net.piotrturski.mintaka;

import java.util.List;


import org.junit.runner.Description;
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
	
}

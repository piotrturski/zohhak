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
		List<FrameworkMethod> annotatedMethods = new MethodAdder().addParametrizedMethods(getTestClass());
		annotatedMethods.addAll(super.computeTestMethods());
		return annotatedMethods;
	}

	@Override
	protected Description describeChild(FrameworkMethod method) {
		TestWith testWithAnnotation = method.getAnnotation(TestWith.class);
		if (testWithAnnotation == null) {
			return super.describeChild(method);
		}
		return Description.createTestDescription(getTestClass().getClass(), ((ParametrizedFrameworkMethod) method).getDescriptionName(), method.getAnnotations());
	}
	
}

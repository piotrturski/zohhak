package net.piotrturski.mintaka;

import java.util.Collections;
import java.util.List;

import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

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

	
}

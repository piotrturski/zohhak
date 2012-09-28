package net.piotrturski.mintaka.internal.junit;

import java.lang.reflect.Method;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.internal.CoercingService;
import net.piotrturski.mintaka.internal.Executor;

import org.junit.runners.model.FrameworkMethod;

public class ParametrizedFrameworkMethod extends FrameworkMethod {

	private final int index;
	private Executor executor = Executor.getExecutorSingleton();

	public ParametrizedFrameworkMethod(Method method, int index, Configuration configuration) {
		super(method);
		this.index = index;
	}
	
	@Override
	public Object invokeExplosively(Object target, Object... params) throws Throwable {
		Object[] parameters = executor.calculateParameters(getMethod(), getParametersLine());
		//Object[] parameters = prepareParameters();
		return super.invokeExplosively(target, parameters);
	}

	public String getParametersLine() {
		TestWith runWithAnnotation = getAnnotation(TestWith.class);
		String parametersLine = runWithAnnotation.value()[index];
		return parametersLine;
	}
	
}

package net.piotrturski.mintaka.internal.junit;

import java.lang.reflect.Method;

import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.internal.CoercingService;

import org.junit.runners.model.FrameworkMethod;

public class ParametrizedFrameworkMethod extends FrameworkMethod {

	private final int index;

	public ParametrizedFrameworkMethod(Method method, int index) {
		super(method);
		this.index = index;
	}
	
	@Override
	public Object invokeExplosively(Object target, Object... params) throws Throwable {
		Object[] parameters = prepareParameters();
		return super.invokeExplosively(target, parameters);
	}

	private Object[] prepareParameters() {
		String parametersLine = getParametersLine();
		return new CoercingService().prepare(parametersLine, this.getMethod().getGenericParameterTypes(), null);
	}


	public String getParametersLine() {
		TestWith runWithAnnotation = getAnnotation(TestWith.class);
		String parametersLine = runWithAnnotation.value()[index];
		return parametersLine;
	}
	
}

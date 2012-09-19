package net.piotrturski.mintaka.internal.junit;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

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
		Object[] parameters = null;
		TestWith testWithAnnotation = getAnnotation(TestWith.class);
		String parametersLine = testWithAnnotation.value()[index];
		return new CoercingService().prepare(parametersLine, this.getMethod().getGenericParameterTypes(), null);
	}

	public String getDescriptionName() {
		TestWith runWithAnnotation = getAnnotation(TestWith.class);
		String parametersLine = runWithAnnotation.value()[index];
		return getName()+" ["+parametersLine+"]";
	}
}

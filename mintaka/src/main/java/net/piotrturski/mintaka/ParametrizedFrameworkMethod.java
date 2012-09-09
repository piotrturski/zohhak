package net.piotrturski.mintaka;

import java.lang.reflect.Method;

import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;

public class ParametrizedFrameworkMethod extends FrameworkMethod {

	private final int index;

	public ParametrizedFrameworkMethod(Method method, int index) {
		super(method);
		this.index = index;
	}
	
	@Override
	public Object invokeExplosively(Object target, Object... params) throws Throwable {
		TestWith runWithAnnotation = getAnnotation(TestWith.class);
		String parametersLine = runWithAnnotation.value()[index];
		int param = Integer.parseInt(parametersLine);
		return super.invokeExplosively(target, new Object[]{param});
	}

}

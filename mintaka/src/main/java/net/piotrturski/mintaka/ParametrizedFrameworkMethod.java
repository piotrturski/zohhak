package net.piotrturski.mintaka;

import java.lang.reflect.Method;

import org.junit.runners.model.FrameworkMethod;

public class ParametrizedFrameworkMethod extends FrameworkMethod {

	private final int index;

	public ParametrizedFrameworkMethod(Method method, int index) {
		super(method);
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public Object invokeExplosively(Object target, Object... params) throws Throwable {
		return super.invokeExplosively(target, new Object[]{1});
	}

}

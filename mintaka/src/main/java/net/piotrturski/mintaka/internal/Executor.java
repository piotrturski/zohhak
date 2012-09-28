package net.piotrturski.mintaka.internal;

import java.lang.reflect.Method;

import net.piotrturski.mintaka.Configuration;

public final class Executor {

	private static Executor executor;
	Parser parser;
	
	static {
		executor = new Executor();
	}
	
	private Executor() {}
	
	public static Executor getExecutorSingleton() {
		return executor;
	}
	
	public Configuration calculateConfiguration(Method method, int index) {
		return null;
	}

	public Object[] calculateParameters(Method method, String parametersLine) {
		//new SingleTestMethod(method, index)
		//String parametersLine = getParametersLine();
		//parser.split();
		return new CoercingService().prepare(parametersLine, method.getGenericParameterTypes(), null);
	}
	
	
	
}

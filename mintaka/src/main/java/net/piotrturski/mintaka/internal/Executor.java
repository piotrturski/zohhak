package net.piotrturski.mintaka.internal;

import java.lang.reflect.Method;

import net.piotrturski.mintaka.Configuration;

public final class Executor {

	Parser parser = new Parser();
	ConfigurationResolver configurationResolver = new ConfigurationResolver();
	
	public Configuration calculateConfiguration(Method method, int index) {
		return null;
	}

	public Object[] calculateParameters(SingleTestMethod singleTestMethod, String parametersLine) {
		Configuration configuration = configurationResolver.calculateConfiguration();
		
		
		//parser.split(input, expectedArgumentsNumber, configuration);
		//new SingleTestMethod(method, index)
		//String parametersLine = getParametersLine();
		//parser.split();
		return new CoercingService().prepare(parametersLine, singleTestMethod.realMethod.getGenericParameterTypes(), null);
	}
	
	
	
}

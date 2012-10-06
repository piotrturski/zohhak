package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.internal.coercing.CoercingService;

public final class Executor {

	Parser parser;
	ConfigurationResolver configurationResolver;
	CoercingService coercingService;
	
	public Object[] calculateParameters(SingleTestMethod singleTestMethod, String parametersLine) {
		Configuration configuration = configurationResolver.calculateConfiguration(singleTestMethod);
		singleTestMethod.configuration = configuration;
		
		String[] splitedParameters = parser.split(singleTestMethod);
		singleTestMethod.splitedParameters = splitedParameters;
		return coercingService.coerceParameters(singleTestMethod);
	}
	
}

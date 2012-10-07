package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.Configuration;

public final class Executor {

	Parser parser;
	ConfigurationResolver configurationResolver;
	CoercingService coercingService;
	
	public Object[] calculateParameters(SingleTestMethod singleTestMethod, String parametersLine) {
		/* 
		 * infrastructure.get(singleTestMethod())
		 * if found then we runningBox else prepare runningBox
		 * config, parsers, coercions
		 * 
		 *   runningBox.run(testMethod)
		 * 
		 */
		
		Configuration configuration = configurationResolver.calculateConfiguration(singleTestMethod);
		singleTestMethod.configuration = configuration;
		
		String[] splitedParameters = parser.split(singleTestMethod);
		singleTestMethod.splitedParameters = splitedParameters;
		return coercingService.coerceParameters(singleTestMethod);
	}
	
}

package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.internal.coercing.CoercingService;
import net.piotrturski.mintaka.internal.model.SingleTestMethod;
import net.piotrturski.mintaka.internal.parsing.ParsingService;

public final class Executor {

	ConfigurationResolver configurationResolver;
	CoercingService coercingService;
	ParsingService parsingService;
	
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
		
		String[] splitedParameters = parsingService.split(singleTestMethod);
		return coercingService.coerceParameters(singleTestMethod, splitedParameters);
	}
	
}

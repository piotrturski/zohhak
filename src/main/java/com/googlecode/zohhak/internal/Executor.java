package com.googlecode.zohhak.internal;

import com.googlecode.zohhak.internal.coercing.CoercingService;
import com.googlecode.zohhak.internal.model.ConfigurationBuilder;
import com.googlecode.zohhak.internal.model.SingleTestMethod;
import com.googlecode.zohhak.internal.parsing.ParsingService;

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
		
		ConfigurationBuilder configuration = configurationResolver.calculateConfiguration(singleTestMethod);
		singleTestMethod.configuration = configuration;
		
		String[] splitedParameters = parsingService.split(singleTestMethod);
		return coercingService.coerceParameters(singleTestMethod, splitedParameters);
	}
	
}

package com.googlecode.zohhak.internal;

import com.googlecode.zohhak.api.backend.ConfigurationBuilder;
import com.googlecode.zohhak.api.backend.ConfigurationResolver;
import com.googlecode.zohhak.api.backend.ParameterCalculator;
import com.googlecode.zohhak.internal.coercing.CoercingService;
import com.googlecode.zohhak.internal.parsing.ParsingService;

import java.lang.reflect.Method;

public final class Executor implements ParameterCalculator {

	ConfigurationResolver configurationResolver;
	CoercingService coercingService;
	ParsingService parsingService;

	public Object[] calculateParameters(String parametersLine, Method testMethod) {
		/* 
		 * infrastructure.get(singleTestMethod())
		 * if found then we runningBox else prepare runningBox
		 * config, parsers, coercions
		 * 
		 *   runningBox.run(testMethod)
		 * 
		 */

		ConfigurationBuilder configuration = configurationResolver.calculateConfiguration(testMethod);
		String[] splitedParameters = parsingService.split(parametersLine, configuration, testMethod);
		return coercingService.coerceParameters(splitedParameters, configuration, testMethod);
	}

}

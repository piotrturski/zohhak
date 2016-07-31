package com.googlecode.zohhak.internal.parsing;

import com.googlecode.zohhak.api.backend.ConfigurationBuilder;

import java.lang.reflect.Method;

public class ParsingService {

	private Parser parser = new Parser();//TODO export as a configuration parameter?
	
	public String[] split(String parametersLine, ConfigurationBuilder configuration, Method realMethod) {
		String[] splitedParameters;
		
		try {
			
			splitedParameters = parser.split(parametersLine, configuration);
			
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("Cannot parse parameters", e);
		}

		int numberOfDeclaredParameters = realMethod.getParameterTypes().length;
		if (splitedParameters.length != numberOfDeclaredParameters) {
			throw new IllegalArgumentException(numberOfDeclaredParameters+" parameter(s) declared but provided "+splitedParameters.length);
		}
		return splitedParameters;
	}
	
}

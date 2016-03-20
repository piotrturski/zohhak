package com.googlecode.zohhak.internal.coercing;

import com.googlecode.zohhak.api.backend.ParameterCoercer;
import com.googlecode.zohhak.api.backend.ParameterCoercerFactory;
import com.googlecode.zohhak.api.backend.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* start with string. add everything that starts from string.
 * while true (not converted)
 * 		check if we have matching tuple (if we reached tartgetType). generics?!
 * 			if yes - try to use it without exception and result
 * 		add everything that starts from the end of existing tuples and remove all tuples from that iteration (shortest)
 * end of while
 * throw coercion failed
 */

public class CoercingService {

	private final ParameterCoercerFactory parameterCoercerFactory;

	public CoercingService(ParameterCoercerFactory parameterCoercerFactory) {
		this.parameterCoercerFactory = parameterCoercerFactory;
	}

	public Object[] coerceParameters(String[] splitedParameters, ConfigurationBuilder configuration, Method testMethod) {
		int numberOfParams = testMethod.getGenericParameterTypes().length;

		DefaultParameterCoercer defaultParameterCoercer = new DefaultParameterCoercer(configuration, testMethod);
		ParameterCoercer parameterCoercer = parameterCoercerFactory.parameterCoercer(defaultParameterCoercer);

		Object[] parameters = new Object[numberOfParams];
		for (int i = 0; i < numberOfParams; i++) {
			String inputString = splitedParameters[i];
			Type parameterType = testMethod.getGenericParameterTypes()[i];
			parameters[i] = parameterCoercer.coerceParameter(parameterType, inputString);
		}
		return parameters;
	}
}

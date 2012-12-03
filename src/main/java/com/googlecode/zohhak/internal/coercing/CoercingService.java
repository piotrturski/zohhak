package com.googlecode.zohhak.internal.coercing;

import static com.googlecode.zohhak.internal.coercing.Result.FAILURE;

import java.lang.reflect.Type;
import java.util.List;


import org.apache.tapestry5.plastic.PlasticUtils;

import com.googlecode.zohhak.internal.model.Logger;
import com.googlecode.zohhak.internal.model.SingleTestMethod;

/* start with string. add everything that starts from string. 
 * while true (not converted)
 * 		check if we have matching tuple (if we reached tartgetType). generics?!
 * 			if yes - try to use it without exception and result
 * 		add everything that starts from the end of existing tuples and remove all tuples from that iteration (shortest)
 * end of while
 * throw coercion failed
 */

public class CoercingService {

	private CoercionHandler coercionHandler = new CoercionHandler();
	private Logger log = new Logger();

	public Object[] coerceParameters(SingleTestMethod method, String[] splitedParameters) {
		log.log("coercing method "+method);
		List<Coercion> methodCoercions = coercionHandler.findCoercionsForMethod(method);
		int numberOfParams = method.getArity();

		Object[] parameters = new Object[numberOfParams];
		for (int i = 0; i < numberOfParams; i++) {
			String inputString = splitedParameters[i];
			Type parameterType = method.getParameterType(i);
			parameters[i] = coerceParameter(parameterType, inputString, methodCoercions);
		}
		return parameters;
	}

	Object coerceParameter(Type type, String stringToParse, List<Coercion> methodCoercions) {
		log.log("coercing \""+stringToParse+"\" to "+type);
		try {
			if ("null".equalsIgnoreCase(stringToParse)) {
				return null;
			}
			if (type instanceof Class) {
				Class<?> targetType = PlasticUtils.toWrapperType((Class<?>) type);

				Result execution = tryToUseCoercions(targetType, stringToParse, methodCoercions);
				if (execution.succeeded()) {
					return execution.getResult();
				}

				if (targetType.isEnum()) {
					return instantiateEnum(stringToParse, targetType);
				}
			}
		} catch (RuntimeException e) {
			throw new IllegalArgumentException(coercingExceptionMessage(stringToParse, type), e);
		}
		throw new IllegalArgumentException(coercingExceptionMessage(stringToParse, type));
	}

	private Result tryToUseCoercions(Class<?> targetType, String stringToParse, List<Coercion> methodCoercions) {
		// TODO maybe index coercions by target type? will it be useful with future extended coercing?
		for (Coercion coercion : methodCoercions) {
			Result execution = tryToUseCoercion(coercion, targetType, stringToParse);
			if (execution.succeeded()) {
				return execution;
			}
		}
		return FAILURE;
	}

	private Result tryToUseCoercion(Coercion coercion, Class<?> targetType, String stringToParse) {
		if (targetType.isAssignableFrom(coercion.getTargetType())) {
			return coercionHandler.invokeCoercion(coercion, stringToParse);
		}
		return FAILURE;
	}

	// unable to instantiate Enum from Class<?> without warnings
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object instantiateEnum(String stringToParse, Class<?> targetType) {
		return Enum.valueOf((Class) targetType, stringToParse);
	}

	private String coercingExceptionMessage(String stringToParse, Type targetType) {
		return String.format("cannot interpret string \"%s\" as a %s", stringToParse, targetType);
	}

}

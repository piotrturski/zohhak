package com.googlecode.zohhak.internal.coercing;

import com.googlecode.zohhak.internal.model.SingleTestMethod;
import org.apache.commons.lang3.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static com.googlecode.zohhak.internal.coercing.Result.FAILURE;

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
    private String[] reservedAnnotations = {
            "com.googlecode.zohhak.api.ignoreZohhak",
            "mockit.Capturing",
            "mockit.Mocked",
    };

    private List reservedAnnotationsList = Arrays.asList(reservedAnnotations);

	public Object[] coerceParameters(SingleTestMethod method, String[] splitedParameters) {
		List<Coercion> methodCoercions = coercionHandler.findCoercionsForMethod(method);
		int numberOfParams = method.getArity();
//        int numberOfParams = splitedParameters.length;

        if (splitedParameters.length > numberOfParams)
            throw new IllegalArgumentException(numberOfParams+" parameter(s) declared but provided "+splitedParameters.length);
        Object[] parameters = new Object[numberOfParams];
        for (int i = 0; i < numberOfParams; i++) {
            try
            {
                String inputString = splitedParameters[i];
                Type parameterType = method.getParameterType(i);
                parameters[i] = coerceParameter(parameterType, inputString, methodCoercions);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                if (!isInReservedAnnotations(method.realMethod.getParameters()[i])){
                    throw new IllegalArgumentException(numberOfParams+" parameter(s) declared but provided "+splitedParameters.length);
                }
                Type parameterType = method.getParameterType(i);
                try
                {
                    parameters[i] = Class.forName(parameterType.getTypeName()).newInstance();
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException f)
                {
                    System.out.println(f);
                }
            }
		}
		return parameters;
	}

    private Boolean isInReservedAnnotations(Parameter param){
        for (Annotation paramAnnotation: param.getAnnotations()){
            if(reservedAnnotationsList.contains(paramAnnotation.annotationType().getName()))
                return true;
        }
        return false;
    }

	Object coerceParameter(Type type, String stringToParse, List<Coercion> methodCoercions) {
		try {
			if (type instanceof Class) {
				Class<?> targetType = ClassUtils.primitiveToWrapper((Class<?>) type);

				Result execution = tryToUseCoercions(targetType, stringToParse, methodCoercions);
				if (execution.succeeded()) {
					return execution.getResult();
				}

				if (targetType.isEnum()) {
					return instantiateEnum(stringToParse, targetType);
				}
			}
            if (stringToParse == null) {
                return null;
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

package com.googlecode.zohhak.internal.coercing;

import com.googlecode.zohhak.api.backend.ParameterCoercer;
import com.googlecode.zohhak.api.backend.ConfigurationBuilder;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.googlecode.zohhak.internal.coercing.Result.FAILURE;

public class DefaultParameterCoercer implements ParameterCoercer {

    private final List<Coercion> methodCoercions;
    private final CoercionHandler coercionHandler = new CoercionHandler();

    public DefaultParameterCoercer(ConfigurationBuilder configuration, Method testMethod) {
        this.methodCoercions = coercionHandler.findCoercionsForMethod(configuration, testMethod);
    }

    public Object coerceParameter(Type type, String stringToParse) {
        try {
            if (stringToParse == null) {
                return null;
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                return coerceParameter(rawType, stringToParse);
            }
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

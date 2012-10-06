package net.piotrturski.mintaka.internal.coercing;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import net.piotrturski.mintaka.internal.SingleTestMethod;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tapestry5.plastic.PlasticUtils;

/* start with string. add everything that starts from string. 
 * while true (not converted)
 * 		check if we have matching tuple (if we reached tartgetType). generics?!
 * 			if yes - try to use it without exception and result
 * 		add everything that starts from the end of existing tuples and remove all tuples from that iteration (shortest)
 * end of while
 * throw coercion failed
 */

public class CoercingService {

	public Cache cache;
	private static final Class<?>[] COERCION_PARAMETERS = new Class<?>[] { String.class };

	public Object[] coerceParameters(SingleTestMethod method) {
		initCoercions(method);
		return coerceParameters(method.realMethod.getGenericParameterTypes(), method.splitedParameters);
	}

	private void initCoercions(SingleTestMethod singleTestMethod) {
		cache.clearMethodData();
		List<Class<?>> coercers = singleTestMethod.configuration.getCoercers();
		for (Class<?> clazz : coercers) {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				addIfCoercingMethod(method);
			}
		}
	}

	private void addIfCoercingMethod(Method method) {
		if (isValidCoercionMethod(method)) {
			Coercion coercion = new Coercion(method);
			cache.addCoercion(coercion);
		}
	}

	boolean isValidCoercionMethod(Method method) {
		Class<?>[] parameters = method.getParameterTypes();
		return ArrayUtils.isEquals(parameters, COERCION_PARAMETERS) && method.getReturnType() != Void.class;
	}

	private Object[] coerceParameters(Type[] genericParameterTypes, String[] parametersToParse) {
		int numberOfParams = parametersToParse.length;
		Object[] parameters = new Object[numberOfParams];
		for (int i = 0; i < numberOfParams; i++) {
			parameters[i] = coerceParameter(genericParameterTypes[i], parametersToParse[i]);
		}
		return parameters;
	}

	Object coerceParameter(Type type, String stringToParse) {
		try {
			if ("null".equalsIgnoreCase(stringToParse)) {
				return null;
			}
			if (type instanceof Class) {
				Class<?> targetType = PlasticUtils.toWrapperType((Class<?>) type);

				Coercion foundCoercion = null;
				for (Coercion coercion : cache.getCoercions()) {
					if (targetType.isAssignableFrom(coercion.getTargetType())) {
						foundCoercion = coercion;
						break;
					}
				}

				if (foundCoercion != null) {
					Object coercerInstance = cache.getCoercerInstance(foundCoercion);
					return foundCoercion.coercionMethod.invoke(coercerInstance, stringToParse);
				}

				if (targetType.isEnum()) {
					return instantiateEnum(stringToParse, targetType);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(createCoercionExceptionMessage(stringToParse, type), e);
		}
		throw new IllegalArgumentException(createCoercionExceptionMessage(stringToParse, type));
	}

	// unable to instantiate Enum from Class<?> without warnings
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object instantiateEnum(String stringToParse, Class<?> targetType) {
		return Enum.valueOf((Class) targetType, stringToParse);
	}

	private String createCoercionExceptionMessage(String stringToParse, Type targetType) {
		return String.format("cannot interpret string %s as a %s", stringToParse, targetType);
	}

}

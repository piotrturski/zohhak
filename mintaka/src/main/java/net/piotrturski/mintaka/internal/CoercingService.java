package net.piotrturski.mintaka.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tapestry5.plastic.PlasticUtils;

public class CoercingService {

	private Map<Class<?>, Method> coercions = new LinkedHashMap<Class<?>, Method>();
	private Object coercerInstance;

	private void initCoercions(SingleTestMethod singleTestMethod) {
		coercions.clear();
		Class<?>[] coercers = singleTestMethod.annotation.coercer();
		Class<?> clazz = coercers[0];
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			addIfCoercingMethod(method);
		}
		try {
			coercerInstance = clazz.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("cannot instantiate coercer", e);
		}
	}

	private void addIfCoercingMethod(Method method) {
		if (method.getParameterTypes().length != 1) {
			return;
		}
		Class<?> clazz = method.getParameterTypes()[0];
		if (clazz != String.class) {
			return;
		}
		if (method.getReturnType() == Void.class) {
			return;
		}
		coercions.put(PlasticUtils.toWrapperType(method.getReturnType()), method);
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

				Method coercionMethod = null;
				for (Entry<Class<?>, Method> entry : coercions.entrySet()) {
					if (targetType.isAssignableFrom(entry.getKey())) {
						coercionMethod = entry.getValue();
						break;
					}
				}

				if (coercionMethod != null) {
					return coercionMethod.invoke(coercerInstance, stringToParse);
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

	@SuppressWarnings({ "unchecked", "rawtypes" }) //unable to instantiate Enum from Class<?> without warnings
	private Object instantiateEnum(String stringToParse, Class<?> targetType) {
		return Enum.valueOf((Class) targetType, stringToParse);
	}

	public Object[] coerceParameters(SingleTestMethod method) {
		initCoercions(method);
		return coerceParameters(method.realMethod.getGenericParameterTypes(), method.splitedParameters);
	}
	
	private String createCoercionExceptionMessage(String stringToParse, Type targetType) {
		return String.format("cannot interpret string %s as a %s", stringToParse, targetType);
	}

}

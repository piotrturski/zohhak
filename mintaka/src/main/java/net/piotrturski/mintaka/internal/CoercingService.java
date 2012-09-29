package net.piotrturski.mintaka.internal;

import java.lang.reflect.Type;

import org.apache.tapestry5.plastic.PlasticUtils;

public class CoercingService {

	private Object[] coerceParameters(Type[] genericParameterTypes, String[] parametersToParse) {
		int numberOfParams = parametersToParse.length;
		Object[] parameters = new Object[numberOfParams];
		for (int i = 0; i < numberOfParams; i++) {
			parameters[i] = parse(genericParameterTypes[i], parametersToParse[i]);
		}
		return parameters;
	}
	
	Object parse(Type type, String stringToParse) {
		if ("null".equalsIgnoreCase(stringToParse)) return null;
		if (type instanceof Class) {
			Class<?> targetType = PlasticUtils.toWrapperType((Class<?>) type);
			
			if (targetType.isAssignableFrom(String.class)) return stringToParse;
			if (targetType.isAssignableFrom(Integer.class)) return Integer.parseInt(stringToParse);
			if (targetType.isAssignableFrom(Long.class)) return Long.parseLong(stringToParse);
			
			if (targetType.isEnum()) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Enum createdEnum = Enum.valueOf((Class)targetType, stringToParse);
				return createdEnum;
			}
			
		}
		throw new IllegalArgumentException("cannot interpret string "+stringToParse+" as a type "+type);
	}

	public Object[] coerceParameters(SingleTestMethod method) {
		return coerceParameters(method.realMethod.getGenericParameterTypes(), method.splitedParameters);
	}
	
}

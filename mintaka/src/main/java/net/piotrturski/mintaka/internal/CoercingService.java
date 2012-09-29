package net.piotrturski.mintaka.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tapestry5.plastic.PlasticUtils;

public class CoercingService {

	private Map<Class<?>, Method> coercions = new LinkedHashMap<Class<?>, Method>(); 
	
	private void initCoercions() {
		coercions.clear();
		Class<?> clazz = DefaultCoercer.class;
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			addIfCoercingMethod(method);
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
		if ("null".equalsIgnoreCase(stringToParse)) {return null;}
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
				try {
					return coercionMethod.invoke(new DefaultCoercer(), stringToParse);
				} catch (Exception e) {
					throw new IllegalArgumentException(e);
				}
			}
			
//			if (targetType.isAssignableFrom(String.class)) return stringToParse;
//			if (targetType.isAssignableFrom(Integer.class)) return Integer.parseInt(stringToParse);
//			if (targetType.isAssignableFrom(Long.class)) return Long.parseLong(stringToParse);
			
			if (targetType.isEnum()) {
				return instantiateEnum(stringToParse, targetType);
			}
			
		}
		throw new IllegalArgumentException("cannot interpret string "+stringToParse+" as a type "+type);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object instantiateEnum(String stringToParse, Class<?> targetType) {
		return Enum.<Enum>valueOf((Class)targetType, stringToParse);
	}

	public Object[] coerceParameters(SingleTestMethod method) {
		Class<?>[] configurations = method.annotation.configuration();
		initCoercions();
		return coerceParameters(method.realMethod.getGenericParameterTypes(), method.splitedParameters);
	}
	
}

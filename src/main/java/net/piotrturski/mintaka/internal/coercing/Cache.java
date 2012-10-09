package net.piotrturski.mintaka.internal.coercing;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {

	private Map<Method, List<Coercion>> coercionsForMethod = new HashMap<Method, List<Coercion>>();
	private Map<Class<?>, Object> coercerInstances = new HashMap<Class<?>, Object>();

	public void setCoercionsForTestMethod(Method realMethod, List<Coercion> foundCoercions) {
		coercionsForMethod.put(realMethod, foundCoercions);
	}
	
	public List<Coercion> getCoercionsForTestMethod(Method realMethod) {
		return coercionsForMethod.get(realMethod);
	}
	
	public Object invokeCoercion(Coercion coercion, String stringToParse) {
		Object coercerInstance = getCoercerInstance(coercion);
		return coercion.invoke(coercerInstance, stringToParse);
		
	}

	private Object instantiateCoercerClass(Class<?> coercerClass) {
		try {
			return coercerClass.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("cannot instantiate coercer class: " + coercerClass.getCanonicalName(), e);
		}
	}

	private Object getCoercerInstance(Coercion coercion) {
		Class<?> coercerClass = coercion.getCoercerClass();
		Object coercerInstance = coercerInstances.get(coercerClass);
		if (coercerInstance == null) {
			coercerInstance = instantiateCoercerClass(coercerClass);
			coercerInstances.put(coercerClass, coercerInstance);
		}
		return coercerInstance;
	}
	
}
package net.piotrturski.mintaka.internal.coercing;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cache {

	private Map<Class<?>, Coercion> coercions = new LinkedHashMap<Class<?>, Coercion>();
	private Map<Class<?>, Object> coercerInstances = new HashMap<Class<?>, Object>();

	public void clearMethodData() {
		// TODO Auto-generated method stub

	}

	private Object instantiateCoercerClass(Class<?> coercerClass) {
		try {
			return coercerClass.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("cannot instantiate coercer class: " + coercerClass.getCanonicalName(), e);
		}
	}

	public void addCoercion(Coercion coercion) {
		coercions.put(coercion.getTargetType(), coercion);

	}

	public Collection<Coercion> getCoercions() {
		return coercions.values();
	}

	public Object getCoercerInstance(Coercion coercion) {
		Class<?> coercerClass = coercion.getCoercerClass();
		Object coercerInstance = coercerInstances.get(coercerClass);
		if (coercerInstance == null) {
			coercerInstance = instantiateCoercerClass(coercerClass);
			coercerInstances.put(coercerClass, coercerInstance);
		}
		return coercerInstance;
	}

}

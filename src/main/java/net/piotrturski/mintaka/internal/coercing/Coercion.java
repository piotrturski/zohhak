package net.piotrturski.mintaka.internal.coercing;

import java.lang.reflect.Method;

import org.apache.tapestry5.plastic.PlasticUtils;

public class Coercion {

	private Cache cache;
	public final Method coercionMethod; //FIXME
	private final Class<?> targetType;

	public Coercion(Cache cache, Method coercionMethod) {
		this.cache = cache;
		this.coercionMethod = coercionMethod;
		targetType = PlasticUtils.toWrapperType(coercionMethod.getReturnType());
		
	}
	
	public Class<?> getTargetType() {
		return targetType;
	}
	
	public Class<?> getCoercerClass() {
		return coercionMethod.getDeclaringClass();
	}

	public Object coerce(String stringToParse) throws Exception {
		Object coercerInstance = cache.getCoercerInstance(this);
		return coercionMethod.invoke(coercerInstance, stringToParse);
	}
	
	@Override
	public String toString() {
		return coercionMethod.toGenericString();
	}
	
}

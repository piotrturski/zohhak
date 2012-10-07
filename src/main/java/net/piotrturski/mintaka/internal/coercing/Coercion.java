package net.piotrturski.mintaka.internal.coercing;

import java.lang.reflect.Method;

import org.apache.tapestry5.plastic.PlasticUtils;

class Coercion {

	public final Method coercionMethod; //FIXME
	private final Class<?> targetType;

	public Coercion(Method coercionMethod) {
		this.coercionMethod = coercionMethod;
		targetType = PlasticUtils.toWrapperType(coercionMethod.getReturnType());
		
	}
	
	public Class<?> getTargetType() {
		return targetType;
	}
	
	public Class<?> getCoercerClass() {
		return coercionMethod.getDeclaringClass();
	}

	@Override
	public String toString() {
		return coercionMethod.toGenericString();
	}
	
}

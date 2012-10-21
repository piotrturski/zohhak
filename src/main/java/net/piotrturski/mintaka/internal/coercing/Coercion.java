package net.piotrturski.mintaka.internal.coercing;

import java.lang.reflect.Method;

import org.apache.tapestry5.plastic.PlasticUtils;

class Coercion {

	private final Method coercionMethod;
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

	public Object invoke(Object instance, String parameter) {
		try {
			return coercionMethod.invoke(instance, parameter);
		} catch (Exception e) {
			throw new IllegalStateException("cannot invoke coercion method: " + coercionMethod, e);
		}
	}

	private Class<?> getSourceType() {
		return coercionMethod.getParameterTypes()[0];
	}
	
	@Override
	public String toString() {
		return getSourceType().getSimpleName() +" -> " + getTargetType().getSimpleName() + " ("+ coercionMethod.getName()+")";
	}
	
}

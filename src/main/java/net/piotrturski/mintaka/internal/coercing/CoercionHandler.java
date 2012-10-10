package net.piotrturski.mintaka.internal.coercing;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.piotrturski.mintaka.internal.model.SingleTestMethod;

import org.apache.commons.lang3.ArrayUtils;

class CoercionHandler {

	private static final Class<?>[] COERCION_PARAMETERS_SIGNATURE = new Class<?>[] { String.class };

	List<Coercion> findCoercionsForMethod(SingleTestMethod method) {
		List<Coercion> methodCoercions = new ArrayList<Coercion>();

		List<Class<?>> coercers = method.configuration.getCoercers();
		methodCoercions = findCoercionsInCoercers(coercers); // TODO cache all coercions for class
		List<Coercion> inTestCoercions = findCoercionsInTestClass(method.realMethod.getDeclaringClass());
		// TODO cache all coercions for class
		methodCoercions.addAll(inTestCoercions);

		return methodCoercions;
	}

	List<Coercion> findCoercionsInTestClass(Class<?> testClass) {
		List<Coercion> foundCoercions = new ArrayList<Coercion>();
		Method[] methods = testClass.getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(net.piotrturski.mintaka.Coercion.class) != null && isValidCoercionMethod(method)) {
				foundCoercions.add(new Coercion(method));
			}
		}
		return foundCoercions;
	}

	List<Coercion> findCoercionsInCoercers(List<Class<?>> coercers) {
		List<Coercion> foundCoercions = new ArrayList<Coercion>();
		for (Class<?> clazz : coercers) {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if (isValidCoercionMethod(method)) {
					foundCoercions.add(new Coercion(method));
				}
			}
		}
		return foundCoercions;
	}

	boolean isValidCoercionMethod(Method method) {
		Class<?>[] parameters = method.getParameterTypes();
		return ArrayUtils.isEquals(parameters, COERCION_PARAMETERS_SIGNATURE) && method.getReturnType() != Void.TYPE;
	}
	
	public Result invokeCoercion(Coercion coercion, String stringToParse) {
		try {
			Class<?> coercerClass = coercion.getCoercerClass();
			Object coercerInstance = coercerClass.newInstance();  // TODO cache coercers instances
			Object coercionResult = coercion.invoke(coercerInstance, stringToParse); // TODO maybe cache result by type, input and configuration
			return Result.success(coercionResult);
		} catch (Exception e) {
			return Result.FAILURE;
		}
	}

}

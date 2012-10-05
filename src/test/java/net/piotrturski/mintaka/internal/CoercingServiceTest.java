package net.piotrturski.mintaka.internal;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import junit.framework.Assert;

import net.piotrturski.mintaka.MintakaRunner;
import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.helper.CoercionCandidates;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

@RunWith(MintakaRunner.class)
public class CoercingServiceTest {

	CoercingService coercingService = new CoercingService();
	
	@Test(expected=IllegalArgumentException.class)
	public void notSupportedType() {
		coercingService.coerceParameter(Thread.class, "");
	}

	@Test(expected=IllegalArgumentException.class)
	public void notClass() throws Exception {
		Type nonClassType = getNonClassType(); 
		coercingService.coerceParameter(nonClassType, "");
	}
	
	private Type getNonClassType() throws Exception {
		return getClass().getDeclaredMethod("methodWithNonClassTypeParameter", Object.class).getGenericParameterTypes()[0];
	}
	
	<T> void methodWithNonClassTypeParameter(T parameter) {}

	@TestWith({
		"voidReturn",
		"noParams",
		"tooManyParams",
		"wrongParamType"
	})
	public void invalidCoercionMethod(String name) {
		Method method = getMethod(name);
		assertFalse(coercingService.isValidCoercionMethod(method));
	}
	
	@Test
	public void validCoercionMehod() {
		Method method = getMethod("validCoercion");
		assertTrue(coercingService.isValidCoercionMethod(method));
	}
	
	private Method getMethod(String name) {
		Method[] methods = CoercionCandidates.class.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		throw new IllegalArgumentException("no method named "+name);
	}
	
}

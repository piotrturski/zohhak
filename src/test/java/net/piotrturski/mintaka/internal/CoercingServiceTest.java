package net.piotrturski.mintaka.internal;

import java.lang.reflect.Type;

import org.junit.Test;

public class CoercingServiceTest {

	CoercingService coercingService = new CoercingService();
	
	@Test(expected=IllegalArgumentException.class)
	public void notSupportedType() {
		coercingService.coerceParameter(Thread.class, "");
	}

	@Test(expected=IllegalArgumentException.class)
	public void notClass() throws Exception {
		Type type = getNonClassType(); 
		coercingService.coerceParameter(type, "");
	}
	
	private Type getNonClassType() throws Exception {
		return getClass().getDeclaredMethod("methodWithNonClassTypeParameter", Object.class).getGenericParameterTypes()[0];
	}
	
	<T> void methodWithNonClassTypeParameter(T parameter) {}
	
}

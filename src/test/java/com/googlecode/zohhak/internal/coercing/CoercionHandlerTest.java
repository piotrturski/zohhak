package com.googlecode.zohhak.internal.coercing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.junit.Test;

import com.googlecode.zohhak.api.Coercion;
import com.googlecode.zohhak.internal.coercing.CoercionHandler;

public class CoercionHandlerTest {

	@Test
	public void testFindCoercionsInTestClass() {
		List<com.googlecode.zohhak.internal.coercing.Coercion> methodCoercions = new CoercionHandler()
				.findCoercionsInTestClass(getClass());
		
		assertThat(methodCoercions).hasSize(1);
		assertThat(methodCoercions.get(0).toString()).contains("validMethod2");
	}

	@Coercion
	public void voidMethod() {
	}

	public void voidMethod2() {
	}

	public int validMethod(String input) {
		return 0;
	}

	@Coercion
	public int validMethod2(String input) {
		return 0;
	}

}

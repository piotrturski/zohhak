package com.googlecode.zohhak.learning;

import static org.apache.commons.lang3.ClassUtils.primitiveToWrapper;
import static org.junit.Assert.*;

import org.junit.Test;

public class TypesTest {

	@Test
	public void learningTestAssignable() {
		assertTrue(int.class.isAssignableFrom(Integer.TYPE));
		assertTrue(Integer.class.isAssignableFrom(Integer.class));
		assertTrue(Integer.TYPE.isAssignableFrom(Integer.TYPE));
		assertTrue(Integer.TYPE.isAssignableFrom(int.class));
		
		assertFalse(Integer.class.isAssignableFrom(Integer.TYPE));
		assertFalse(Integer.TYPE.isAssignableFrom(Integer.class));
		assertFalse(int.class.isAssignableFrom(Integer.class));
		assertFalse(Integer.class.isAssignableFrom(int.class));
		
	}
	
	@Test
	public void classUtilsLearningTest() {
		assertEquals(Integer.class, primitiveToWrapper(int.class));
		assertEquals(Integer.class, primitiveToWrapper(Integer.class));
		
		assertEquals(Runnable.class, primitiveToWrapper(Runnable.class));
	}
	
}

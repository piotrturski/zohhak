package net.piotrturski.mintaka.learning;

import static junit.framework.Assert.*;
import static org.apache.tapestry5.plastic.PlasticUtils.toWrapperType;

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
	public void plasticUtilsLearningTest() {
		assertEquals(Integer.class, toWrapperType(int.class));
		assertEquals(Integer.class, toWrapperType(Integer.class));
		
		assertEquals(Runnable.class, toWrapperType(Runnable.class));
	}
	
}

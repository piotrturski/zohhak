package com.googlecode.zohhak.api;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;

import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.googlecode.zohhak.helper.SampleEnum;
import com.googlecode.zohhak.helper.SampleType;

@RunWith(ZohhakRunner.class)
public class CoercingTest {
	
	@TestWith("ala")
	public void coerceString(String param) {
		assertThat(param).isEqualTo("ala");
	}
	
	@TestWith("7")
	public void coerceInt(int param) {
		assertThat(param).isEqualTo(7);
	}
	
	@TestWith("11")
	public void coerceInteger(Integer param) {
		assertThat(param).isEqualTo(11);
	}
	
	@TestWith("nuLl, null")
	public void coerceNull(Integer param, SampleType sampleType) {
		assertThat(param).isNull();
		assertThat(sampleType).isNull();
	}
	
	@TestWith(" 'null' ")
	public void coerceNotNull(String notNull) {
		assertThat(notNull).isEqualTo("null");
	}
	
	@TestWith("7")
	public void coerceLong(Long param) {
		assertThat(param).isEqualTo(7);		
	}
	
	@TestWith("7")
	public void coerceLongPrimitive(long param) {
		assertThat(param).isEqualTo(7L);
	}
	
	@TestWith("6")
	public void should_accept_object_type(Object param) {
	}
	
	@TestWith("6")
	public void coerceAssignableNumber(Number param) {
		assertThat(param.intValue()).isEqualTo(6);
	}
	
	@TestWith("ala")
	public void coerceCharSequence(CharSequence param) {
		assertThat(param).isInstanceOf(CharSequence.class).isEqualTo("ala");
	}
	
	@TestWith("ONE_OF_ENUM_VALUES")
	public void coerceEnum(SampleEnum param) {
		assertThat(param).isEqualTo(SampleEnum.ONE_OF_ENUM_VALUES);		
	}
	
	@TestWith("c, 10, -13, 15, 1.5, 2.3, 7, true")
	public void coercingPrimitives(char c, byte b, short s, int i, float f, double d, long l, boolean bool) {
		coercingWrappers(c, b, s, i, f, d, l, bool);
	}
	
	@TestWith("c, 10, -13, 15, 1.5, 2.3, 7, true")
	public void coercingWrappers(Character c, Byte b, Short s, Integer i, Float f, Double d, Long l, Boolean bool) {
		assertThat(c).isEqualTo('c');
		assertThat(b).isEqualTo((byte)10);
		assertThat(s).isEqualTo((short)-13);
		assertThat(i).isEqualTo(15);
		assertThat(f).isEqualTo(1.5f, offset(0.0001f));
		assertThat(d).isEqualTo(2.3, offset(0.0001));
		assertThat(l).isEqualTo(7);
		assertThat(bool).isTrue();
	}
	
}

package com.googlecode.zohhak.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.Coercion;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.googlecode.zohhak.helper.SampleType;
import com.googlecode.zohhak.helper.SampleType2;
import com.googlecode.zohhak.helper.SecondCoercer;

@RunWith(ZohhakRunner.class)
public class ExtendedCoercerTest {

	@TestWith(value="a", coercers=SecondCoercer.class)
	public void addedCoercerNewTypeTest(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
	@TestWith(value="a, b", coercers=SecondCoercer.class)
	public void addedCoercerOldTypeTest(SampleType sampleType, String string) {
		assertThat(sampleType.value).isEqualTo("a");
		assertThat(string).isEqualTo("b");
	}
	
	@TestWith(value="0xFF", coercers=SecondCoercer.class)
	public void coerceHex(int param) {
		assertThat(param).isEqualTo(255);		
	}
	
	@TestWith(value="b1101", coercers=SecondCoercer.class)
	public void assertionErrorHandling(int param) {
		assertThat(param).isEqualTo(13);
	}
	
	@TestWith("a")
	public void annotatedAssertionTest(SampleType2 sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
	@TestWith(value="a", coercers=InnerCoercer.class)
	public void innerCoercerTest(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
	@Coercion
	public SampleType2 toSampleType2(String input) {
		return new SampleType2(input);
	}
	
	public static class InnerCoercer {
		
		public SampleType toSampleType(String input) {
			return new SampleType(input);
		}		
	}
}

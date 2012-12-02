package org.zohhak.api;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.zohhak.api.Coercion;
import org.zohhak.api.TestWith;
import org.zohhak.api.runners.ZohhakRunner;
import org.zohhak.helper.SampleType;
import org.zohhak.helper.SampleType2;
import org.zohhak.helper.SecondCoercer;

@RunWith(ZohhakRunner.class)
public class ExtendedCoercerTest {

	@TestWith(value="a", coercer=SecondCoercer.class)
	public void addedCoercerNewTypeTest(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
	@TestWith(value="a, b", coercer=SecondCoercer.class)
	public void addedCoercerOldTypeTest(SampleType sampleType, String string) {
		assertThat(sampleType.value).isEqualTo("a");
		assertThat(string).isEqualTo("b");
	}
	
	@TestWith(value="0xFF", coercer=SecondCoercer.class)
	public void coerceHex(int param) {
		assertThat(param).isEqualTo(255);		
	}
	
	@TestWith(value="b1101", coercer=SecondCoercer.class)
	public void assertionErrorHandling(int param) {
		assertThat(param).isEqualTo(13);
	}
	
	@TestWith("a")
	public void annotatedAssertionTest(SampleType2 sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
	@TestWith(value="a", coercer=InnerCoercer.class)
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

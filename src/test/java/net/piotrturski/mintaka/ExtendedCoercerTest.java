package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.helper.SampleType2;
import net.piotrturski.mintaka.helper.SecondCoercer;
import net.piotrturski.mintaka.helper.SampleType;
import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
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
	
	@Coercion
	public SampleType2 toSampleType2(String input) {
		return new SampleType2(input);
	}
}

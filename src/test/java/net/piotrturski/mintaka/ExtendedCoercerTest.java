package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import net.piotrturski.mintaka.helper.CoercerWithSampleType;
import net.piotrturski.mintaka.helper.SampleType;

@RunWith(MintakaRunner.class)
public class ExtendedCoercerTest {

	@TestWith(value="a", coercer=CoercerWithSampleType.class)
	public void addedCoercerNewTypeTest(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
	@Ignore
	@TestWith(value="a, b", coercer=CoercerWithSampleType.class)
	public void addedCoercerOldTypeTest(SampleType sampleType, String string) {
		assertThat(sampleType.value).isEqualTo("a");
		assertThat(string).isEqualTo("b");
	}
	
}

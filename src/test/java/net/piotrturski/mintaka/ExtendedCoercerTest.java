package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.helper.CoercerWithSampleType;
import net.piotrturski.mintaka.helper.SampleType;
import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
public class ExtendedCoercerTest {

	@TestWith(value="a", coercer=CoercerWithSampleType.class)
	public void addedCoercerNewTypeTest(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
	@TestWith(value="a, b", coercer=CoercerWithSampleType.class)
	public void addedCoercerOldTypeTest(SampleType sampleType, String string) {
		assertThat(sampleType.value).isEqualTo("a");
		assertThat(string).isEqualTo("b");
	}
	
}

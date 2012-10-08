package net.piotrturski.mintaka.programmatic;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.helper.SampleType;
import net.piotrturski.mintaka.helper.SecondCoercer;

@Configure(coercer=SecondCoercer.class)
public class ClassLevelConfiguration {

	@TestWith("a")
	public void samleType(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

package net.piotrturski.mintaka.programmatic;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.helper.SampleConfiguration;
import net.piotrturski.mintaka.helper.SampleConfiguration2;
import net.piotrturski.mintaka.helper.SampleType;

@Configure(configuration=SampleConfiguration2.class)
public class ClassAndMethodConfig {

	@TestWith(value="a", configuration=SampleConfiguration.class)
	public void coercerFromMethodConfig(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

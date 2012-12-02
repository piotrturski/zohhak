package org.zohhak.programmatic;

import static org.fest.assertions.api.Assertions.assertThat;

import org.zohhak.api.Configure;
import org.zohhak.api.TestWith;
import org.zohhak.helper.SampleConfiguration;
import org.zohhak.helper.SampleConfiguration2;
import org.zohhak.helper.SampleType;


@Configure(configuration=SampleConfiguration2.class)
public class ClassAndMethodConfig {

	@TestWith(value="a", configuration=SampleConfiguration.class)
	public void coercerFromMethodConfig(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

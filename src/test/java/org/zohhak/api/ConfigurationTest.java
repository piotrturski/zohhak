package org.zohhak.api;

import static org.fest.assertions.api.Assertions.assertThat;


import org.junit.runner.RunWith;
import org.zohhak.api.TestWith;
import org.zohhak.api.runners.ZohhakRunner;
import org.zohhak.helper.SampleConfiguration;
import org.zohhak.helper.SampleType;

@RunWith(ZohhakRunner.class)
public class ConfigurationTest {

	@TestWith(value="a", configuration=SampleConfiguration.class)
	public void coercerFromMethodConfig(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

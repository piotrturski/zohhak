package org.zohhak;

import static org.fest.assertions.api.Assertions.assertThat;


import org.junit.runner.RunWith;
import org.zohhak.TestWith;
import org.zohhak.helper.SampleConfiguration;
import org.zohhak.helper.SampleType;
import org.zohhak.runners.MintakaRunner;

@RunWith(MintakaRunner.class)
public class ConfigurationTest {

	@TestWith(value="a", configuration=SampleConfiguration.class)
	public void coercerFromMethodConfig(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

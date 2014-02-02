package com.googlecode.zohhak.api;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.googlecode.zohhak.helper.SampleConfiguration;
import com.googlecode.zohhak.helper.SampleType;

@RunWith(ZohhakRunner.class)
public class ConfigurationTest {

	@TestWith(value="a", configuration=SampleConfiguration.class)
	public void coercerFromMethodConfig(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

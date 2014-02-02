package com.googlecode.zohhak.programmatic;

import static org.assertj.core.api.Assertions.assertThat;


import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.helper.SampleConfiguration;
import com.googlecode.zohhak.helper.SampleConfiguration2;
import com.googlecode.zohhak.helper.SampleType;


@Configure(configuration=SampleConfiguration2.class)
public class ClassAndMethodConfig {

	@TestWith(value="a", configuration=SampleConfiguration.class)
	public void coercerFromMethodConfig(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

package com.googlecode.zohhak.programmatic;

import static org.fest.assertions.api.Assertions.assertThat;


import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.helper.SampleType;
import com.googlecode.zohhak.helper.SecondCoercer;


@Configure(coercers=SecondCoercer.class)
public class ClassLevelConfiguration {

	@TestWith("a")
	public void samleType(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

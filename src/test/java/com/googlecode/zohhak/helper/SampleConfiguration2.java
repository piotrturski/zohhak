package com.googlecode.zohhak.helper;

import com.googlecode.zohhak.api.DefaultConfiguration;

public class SampleConfiguration2 extends DefaultConfiguration {

	@Override
	public Class<?>[] coercers() {
		return asArray(CoercerOfSampleType2.class);
	}
	
}

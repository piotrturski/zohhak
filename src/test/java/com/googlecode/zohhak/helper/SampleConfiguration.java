package com.googlecode.zohhak.helper;

import com.googlecode.zohhak.api.DefaultConfiguration;

public class SampleConfiguration extends DefaultConfiguration {

	@Override
	public Class<?>[] coercers() {
		return asArray(SecondCoercer.class);
	}
	
}

package org.zohhak.helper;

import org.zohhak.api.DefaultConfiguration;

public class SampleConfiguration extends DefaultConfiguration {

	@Override
	public Class<?>[] coercer() {
		return asArray(SecondCoercer.class);
	}
	
}

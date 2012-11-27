package org.zohhak.helper;

import org.zohhak.DefaultConfiguration;

public class SampleConfiguration extends DefaultConfiguration {

	@Override
	public Class<?>[] coercer() {
		return asArray(SecondCoercer.class);
	}
	
}

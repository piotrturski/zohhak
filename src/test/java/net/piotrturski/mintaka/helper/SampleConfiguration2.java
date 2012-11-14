package net.piotrturski.mintaka.helper;

import net.piotrturski.mintaka.DefaultConfiguration;

public class SampleConfiguration2 extends DefaultConfiguration {

	@Override
	public Class<?>[] coercer() {
		return asArray(CoercerOfSampleType2.class);
	}
	
}

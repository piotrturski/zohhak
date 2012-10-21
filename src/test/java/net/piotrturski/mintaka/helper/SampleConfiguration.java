package net.piotrturski.mintaka.helper;

import net.piotrturski.mintaka.Configuration;

public class SampleConfiguration extends Configuration {

	@Override
	public Class<?>[] coercer() {
		return asArray(SecondCoercer.class);
	}
	
}

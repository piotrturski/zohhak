package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.internal.model.SingleTestMethod;

public class ConfigurationResolver {

	public Configuration calculateConfiguration(SingleTestMethod singleTestMethod) {
		Configuration configuration = new Configuration();
		Class<?>[] additionalCoercers = singleTestMethod.annotation.coercer();
		
		configuration.addCoercers(additionalCoercers);
		
		return configuration;
	}
	
}

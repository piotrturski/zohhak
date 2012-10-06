package net.piotrturski.mintaka.internal.coercing;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.internal.SingleTestMethod;

public class ConfigurationResolver {

	public Configuration calculateConfiguration(SingleTestMethod singleTestMethod) {
		Configuration configuration = new Configuration();
		Class<?>[] additionalCoercers = singleTestMethod.annotation.coercer();
		
		configuration.addCoercers(additionalCoercers);
		
		return configuration;
	}
	
}

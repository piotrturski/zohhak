package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.internal.model.SingleTestMethod;

public class ConfigurationResolver {

	public Configuration calculateConfiguration(SingleTestMethod singleTestMethod) {
		Configuration configuration = new Configuration();
		
		Configure configure = singleTestMethod.realMethod.getDeclaringClass().getAnnotation(Configure.class);
		if (configure != null) {//TODO move to signleTestMethod
			configuration.addCoercers(configure.coercer());
		}
		
		Class<?>[] additionalCoercers = singleTestMethod.annotation.coercer();
		
		configuration.addCoercers(additionalCoercers);
		
		return configuration;
	}
	
}

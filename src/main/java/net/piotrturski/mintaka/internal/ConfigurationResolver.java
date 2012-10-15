package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.internal.model.SingleTestMethod;

public class ConfigurationResolver {

	public Configuration calculateConfiguration(SingleTestMethod singleTestMethod) {
		Configuration configuration = new Configuration();
		
		Configure classConfiguration = singleTestMethod.realMethod.getDeclaringClass().getAnnotation(Configure.class);
		if (classConfiguration != null) {//TODO move to signleTestMethod
			configuration.addCoercers(classConfiguration.coercer());
			configuration.overrideSeparator(classConfiguration.separator());
		}
		
		TestWith testAnnotation = singleTestMethod.annotation;
		Class<?>[] additionalCoercers = testAnnotation.coercer();
		configuration.addCoercers(additionalCoercers);
		
		configuration.overrideSeparator(testAnnotation.separator());
		
		return configuration;
	}
	
}

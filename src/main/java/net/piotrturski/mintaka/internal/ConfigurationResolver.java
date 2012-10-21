package net.piotrturski.mintaka.internal;

import java.util.ArrayList;
import java.util.List;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.internal.model.ConfigLine;
import net.piotrturski.mintaka.internal.model.ConfigurationBuilder;
import net.piotrturski.mintaka.internal.model.SingleTestMethod;

public class ConfigurationResolver {

	
	public ConfigurationBuilder calculateConfiguration2(SingleTestMethod singleTestMethod) {
		ConfigurationBuilder builder = defaultConfiguration();
		
		Configure classConfiguration = singleTestMethod.realMethod.getDeclaringClass().getAnnotation(Configure.class);
		addConfigure(builder, classConfiguration);
		
		TestWith testAnnotation = singleTestMethod.annotation;
		Class<?>[] additionalCoercers = testAnnotation.coercer();
		
		if (!testAnnotation.inheritCoercers()) {
			builder.removeCoercers();
		}
		
		builder.addCoercers(additionalCoercers);
		
		builder.overrideSeparator(testAnnotation.separator());
		builder.overrideStringBoundary(testAnnotation.stringBoundary());
		
		return builder;
	}

	//TODO test it. refafactor singletestMethod for easier mocking. check if result does not contain repeated classes 
	public ConfigurationBuilder calculateConfiguration(SingleTestMethod singleTestMethod) {
		List<ConfigLine> configLines = addConfigLines(singleTestMethod);
		return mergeConfig(configLines);
	}
	
	private List<ConfigLine> addConfigLines(SingleTestMethod singleTestMethod) {
		List<ConfigLine> configs = new ArrayList<ConfigLine>();
		Configure configure = singleTestMethod.realMethod.getDeclaringClass().getAnnotation(Configure.class);
		
		if (configure != null) {
			addConfigLine(configure.configuration(), configs); //config field from class
			configs.add(new ConfigLine(configure)); //rest fields from class
		}
		
		addConfigLine(singleTestMethod.annotation.configuration(), configs); //config field from method
		configs.add(new ConfigLine(singleTestMethod.annotation)); //rest fields from method
		
		return configs;
	}
	
	private ConfigurationBuilder mergeConfig(List<ConfigLine> configLines) {
		ConfigurationBuilder builder = defaultConfiguration();
		for (ConfigLine configLine : configLines) {
			builder.addConfigLine(configLine);
		}
		return builder;
	}
	
	private void addConfigLine(Class<? extends Configuration> configuration, List<ConfigLine> configs) {
		try {
			Configuration instance = configuration.newInstance(); //TODO cache it
			configs.add(new ConfigLine(instance));
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	private void addConfigure(ConfigurationBuilder builder, Configure configure) {
		if (configure != null) {//TODO move to signleTestMethod
			addConfiguration(builder, configure.configuration());
			builder.addCoercers(configure.coercer());
			builder.overrideSeparator(configure.separator());
			builder.overrideStringBoundary(configure.stringBoundary());
		}
	}

	private void addConfiguration(ConfigurationBuilder builder, Class<? extends Configuration> configuration) {
		try {
			Configuration instance = configuration.newInstance(); //TODO cache it
			if (!instance.inheritCoercers()) {
				builder.getCoercers().clear();
			}
			builder.addCoercers(instance.coercer());
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		
	}

	private ConfigurationBuilder defaultConfiguration() {
		ConfigurationBuilder configuration = new ConfigurationBuilder();
		configuration.addCoercers(configuration.defaultCoercer());
		return configuration;
	}
	
}

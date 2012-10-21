package net.piotrturski.mintaka.internal;

import java.util.ArrayList;
import java.util.List;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.internal.model.ConfigLine;
import net.piotrturski.mintaka.internal.model.ConfigurationBuilder;
import net.piotrturski.mintaka.internal.model.SingleTestMethod;

public class ConfigurationResolver {

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
	
	private ConfigurationBuilder defaultConfiguration() {
		ConfigurationBuilder configuration = new ConfigurationBuilder();
		configuration.addCoercers(configuration.defaultCoercer());
		return configuration;
	}
	
}

package com.googlecode.zohhak.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import com.googlecode.zohhak.api.Configuration;
import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.DefaultConfiguration;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.backend.ConfigurationResolver;
import com.googlecode.zohhak.internal.model.ConfigLine;
import com.googlecode.zohhak.api.backend.ConfigurationBuilder;
import com.googlecode.zohhak.internal.model.ConfigLineConfigurationBuilder;


public class ConfigLineConfigurationResolver implements ConfigurationResolver {

	private final ConfigLine defaultConfigLine = new ConfigLine(new DefaultConfiguration());
	
	//TODO test it. refactor singletestMethod for easier mocking. check if result does not contain repeated classes 
	public ConfigurationBuilder calculateConfiguration(Method testMethod) {
		List<ConfigLine> configLines = addConfigLines(testMethod);
		return mergeConfig(configLines);
	}
	
	private List<ConfigLine> addConfigLines(Method realMethod) {
		List<ConfigLine> configs = new ArrayList<ConfigLine>();
		
		Configure configure = realMethod.getDeclaringClass().getAnnotation(Configure.class);
		if (configure != null) {
			addConfigLine(configure.configuration(), configs); //config field from class
			configs.add(new ConfigLine(configure)); //rest fields from class
		}

		TestWith annotation = realMethod.getAnnotation(TestWith.class);
		addConfigLine(annotation.configuration(), configs); //config field from method
		configs.add(new ConfigLine(annotation)); //rest fields from method

		//TODO @Configure annotation from method
		return configs;
	}
	
	ConfigurationBuilder mergeConfig(List<ConfigLine> configLines) {
		ConfigLineConfigurationBuilder builder = new ConfigLineConfigurationBuilder();
		builder.addConfigLine(defaultConfigLine);
		
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
			throw new IllegalArgumentException("Cannot instantiate configuration class: "+configuration.getName(), e);
		}
	}
	
}

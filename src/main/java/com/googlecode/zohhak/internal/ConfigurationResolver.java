package com.googlecode.zohhak.internal;

import java.util.ArrayList;
import java.util.List;


import com.googlecode.zohhak.api.Configuration;
import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.DefaultConfiguration;
import com.googlecode.zohhak.internal.model.ConfigLine;
import com.googlecode.zohhak.internal.model.ConfigurationBuilder;
import com.googlecode.zohhak.internal.model.SingleTestMethod;


public class ConfigurationResolver {

	private final ConfigLine defaultConfigLine = new ConfigLine(new DefaultConfiguration());
	
	//TODO test it. refactor singletestMethod for easier mocking. check if result does not contain repeated classes 
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
		
		//TODO @Configure annotation from method
		
		return configs;
	}
	
	ConfigurationBuilder mergeConfig(List<ConfigLine> configLines) {
		ConfigurationBuilder builder = new ConfigurationBuilder();
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
			throw new IllegalArgumentException(e);
		}
	}
	
}

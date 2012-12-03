package com.googlecode.zohhak.internal.model;

import com.googlecode.zohhak.api.Configuration;
import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.TestWith;

/**
 * one line of configuration. from @Configure, @TestWith and @Configuration?
 * 
 *
 */
public class ConfigLine {
	
	private final boolean inheritCoercers;
	private final Class<?>[] coercers;
	private final String separator;
	private final String stringBoundary;
	
	public ConfigLine(Configuration instance) {
		inheritCoercers = instance.inheritCoercers();
		coercers = instance.coercers();
		separator = instance.separator();
		stringBoundary = instance.stringBoundary();
	}

	public ConfigLine(Configure configure) {
		inheritCoercers = configure.inheritCoercers();
		coercers = configure.coercers();
		separator = configure.separator();
		stringBoundary = configure.stringBoundary();
	}

	public ConfigLine(TestWith annotation) {
		inheritCoercers = annotation.inheritCoercers();
		coercers = annotation.coercers();
		separator = annotation.separator();
		stringBoundary = annotation.stringBoundary();
	}

	public boolean isInheritCoercers() {
		return inheritCoercers;
	}

	public Class<?>[] getCoercers() {
		return coercers;
	}

	public String getSeparator() {
		return separator;
	}

	public String getStringBoundary() {
		return stringBoundary;
	}

}

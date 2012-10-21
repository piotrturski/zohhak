package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.Configuration;
import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.TestWith;

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
		coercers = instance.coercer();
		separator = instance.separator();
		stringBoundary = instance.stringBoundary();
	}

	public ConfigLine(Configure configure) {
		inheritCoercers = configure.inheritCoercers();
		coercers = configure.coercer();
		separator = configure.separator();
		stringBoundary = configure.stringBoundary();
	}

	public ConfigLine(TestWith annotation) {
		inheritCoercers = annotation.inheritCoercers();
		coercers = annotation.coercer();
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

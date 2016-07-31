package com.googlecode.zohhak.api.backend;

import java.lang.reflect.Method;

public interface ConfigurationResolver {
	ConfigurationBuilder calculateConfiguration(Method testMethod);
}

package com.googlecode.zohhak.internal;

import com.googlecode.zohhak.api.backend.ConfigurationResolver;
import com.googlecode.zohhak.internal.coercing.CoercingService;
import com.googlecode.zohhak.api.backend.ParameterCoercerFactory;
import com.googlecode.zohhak.internal.parsing.ParsingService;

public class IoCContainer {

	private Executor executor;

	public IoCContainer(ParameterCoercerFactory parameterCoercerFactory, ConfigurationResolver configurationResolver) {
		executor = new Executor();
		executor.coercingService = new CoercingService(parameterCoercerFactory);
		executor.configurationResolver = configurationResolver;
		executor.parsingService = new ParsingService();
	}

	public Executor getExecutor() {
		return executor;
	}
}

package org.zohhak.internal;

import org.zohhak.internal.coercing.CoercingService;
import org.zohhak.internal.parsing.ParsingService;

public class IoCContainer {

	Executor executor;

	public IoCContainer() {
		executor = new Executor();
		executor.coercingService = new CoercingService();
		executor.configurationResolver = new ConfigurationResolver();
		executor.parsingService = new ParsingService();
	}

	public Executor getExecutor() {
		return executor;
	}

	
	
}

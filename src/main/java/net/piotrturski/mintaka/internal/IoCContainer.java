package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.internal.coercing.Cache;
import net.piotrturski.mintaka.internal.coercing.CoercingService;
import net.piotrturski.mintaka.internal.coercing.ConfigurationResolver;

public class IoCContainer {

	Executor executor;
	Parser parser;
	ConfigurationResolver configurationResolver;

	public IoCContainer() {
		executor = new Executor();
		parser = new Parser();
		configurationResolver = new ConfigurationResolver();
		
		executor.coercingService = new CoercingService();
		executor.coercingService.cache = new Cache();
		executor.configurationResolver = configurationResolver;
		executor.parser = parser;
	}

	public Executor getExecutor() {
		return executor;
	}

	
	
}

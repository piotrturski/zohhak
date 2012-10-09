package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.internal.coercing.Cache;
import net.piotrturski.mintaka.internal.coercing.CoercingService;
import net.piotrturski.mintaka.internal.parsing.ParsingService;

public class IoCContainer {

	Executor executor;

	public IoCContainer() {
		executor = new Executor();
		executor.coercingService = new CoercingService();
		executor.coercingService.cache = new Cache();
		executor.configurationResolver = new ConfigurationResolver();
		executor.parsingService = new ParsingService();
	}

	public Executor getExecutor() {
		return executor;
	}

	
	
}

package net.piotrturski.mintaka.internal;

public class IoCContainer {

	Executor executor;
	Parser parser;
	ConfigurationResolver configurationResolver;

	public IoCContainer() {
		executor = new Executor();
		parser = new Parser();
		configurationResolver = new ConfigurationResolver();
		
		executor.coercingService = new CoercingService();
		executor.configurationResolver = configurationResolver;
		executor.parser = parser;
	}

	public Executor getExecutor() {
		return executor;
	}

	
	
}

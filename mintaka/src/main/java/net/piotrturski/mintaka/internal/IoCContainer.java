package net.piotrturski.mintaka.internal;

public class IoCContainer {

	Executor executor;
	Parser parser;

	public IoCContainer() {
		executor = new Executor();
		parser = new Parser();
		
		executor.parser = parser;
	}

	public Executor getExecutor() {
		return executor;
	}

	
	
}

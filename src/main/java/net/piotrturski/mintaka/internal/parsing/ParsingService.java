package net.piotrturski.mintaka.internal.parsing;

import net.piotrturski.mintaka.internal.model.SingleTestMethod;

public class ParsingService {

	private Parser parser = new Parser();//TODO export as a configuration parameter?
	
	public String[] split(SingleTestMethod method) {
		String[] splitedParameters;
		
		try {
			
			splitedParameters = parser.split(method);
			
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("Cannot parse parameters", e);
		}
		
		int numberOfDeclaredParameters = method.getArity();
		if (splitedParameters.length != numberOfDeclaredParameters) {
			throw new IllegalArgumentException(numberOfDeclaredParameters+" parameter(s) declared but provided "+splitedParameters.length);
		}
		return splitedParameters;
	}
	
}

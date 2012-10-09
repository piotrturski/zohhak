package net.piotrturski.mintaka.internal.parsing;

import net.piotrturski.mintaka.internal.model.SingleTestMethod;

public class ParsingService {

	private static final String CANNOT_PARSE_PARAMETERS = "Cannot parse parameters";
	private Parser parser = new Parser();//TODO export as a configuration parameter?
	
	public String[] split(SingleTestMethod method) {
		String[] splitedParameters;
		
		try {
			splitedParameters = parser.split(method);
			
		} catch (RuntimeException e) {
			throw new IllegalArgumentException(CANNOT_PARSE_PARAMETERS, e);
		} catch (AssertionError e) {
			throw new IllegalArgumentException(CANNOT_PARSE_PARAMETERS, e);
		}
		
		int numberOfDeclaredParameters = method.realMethod.getGenericParameterTypes().length;
		if (splitedParameters.length != numberOfDeclaredParameters) {
			throw new IllegalArgumentException(numberOfDeclaredParameters+" parameter(s) declared but provided "+splitedParameters.length);
		}
		return splitedParameters;
		
	}
	
}

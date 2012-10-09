package net.piotrturski.mintaka.internal.parsing;

import net.piotrturski.mintaka.internal.model.SingleTestMethod;

public class Parser {

	public String[] split(SingleTestMethod method) {
		String splittingRegexp = "\\s*[,|]\\s*";
		return method.parametersLine.split(splittingRegexp);
		
	}
	
}
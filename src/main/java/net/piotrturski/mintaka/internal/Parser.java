package net.piotrturski.mintaka.internal;

public class Parser {

	public String[] split(SingleTestMethod method) {
		String splittingRegexp = "\\s*[,|]\\s*";
		return method.parametersLine.split(splittingRegexp);
		
	}
	
}

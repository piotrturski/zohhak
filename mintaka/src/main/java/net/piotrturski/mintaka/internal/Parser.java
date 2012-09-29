package net.piotrturski.mintaka.internal;

public class Parser {

	public String[] split(SingleTestMethod method) {
//		String splittingRegexp = "[,|]";
		String splittingRegexp = "\\s*[,|]\\s*";
		String[] splited = method.parametersLine.split(splittingRegexp);
//		for (int i = 0; i < splited.length; i++) {
//			splited[i] = splited[i].trim();
//		}
		return splited;
		
	}
	
}

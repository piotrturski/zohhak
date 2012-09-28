package net.piotrturski.mintaka.internal;

import net.piotrturski.mintaka.Configuration;

public class Parser {

	public String[] split(String input, int expectedArgumentsNumber, Configuration configuration) {
		String[] splited = input.split("[,|]");
		for (int i = 0; i < splited.length; i++) {
			splited[i] = splited[i].trim();
		}
		return splited;
	}
	
}

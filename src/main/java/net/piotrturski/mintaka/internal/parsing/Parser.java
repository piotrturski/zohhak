package net.piotrturski.mintaka.internal.parsing;

import org.apache.commons.lang3.StringUtils;

import net.piotrturski.mintaka.internal.model.SingleTestMethod;

public class Parser {

	private static final String WHITE_CHARS = "\\s*";
	private static final int NEGATIVE = -1;

	public String[] split(SingleTestMethod method) {
		return split(method.parametersLine, method.configuration.getSeparator(), "'");
	}

	String[] split(String parametersLine, String separator, String quotingMarker) {
		String splittingRegexp = WHITE_CHARS + separator + WHITE_CHARS;
		String[] splitted = parametersLine.trim().split(splittingRegexp, NEGATIVE);
		for (int i = 0; i < splitted.length; i++) {
			splitted[i] = removeEdges(splitted[i], quotingMarker);
		}
		return splitted;
	}

	String removeEdges(String input, String toRemove) {
		String frontRemoved = StringUtils.removeStart(input, toRemove);
		return StringUtils.removeEnd(frontRemoved, toRemove);
	}
	
}

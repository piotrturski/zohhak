package com.googlecode.zohhak.internal.parsing;

import org.apache.commons.lang3.StringUtils;

import com.googlecode.zohhak.internal.model.SingleTestMethod;


public class Parser {

	private static final String WHITE_CHARS = "\\s*";
	private static final int NEGATIVE = -1;

	public String[] split(SingleTestMethod method) {
		return split(method.parametersLine, method.configuration.getSeparator(), method.configuration.getStringBoundary());
	}

	String[] split(String parametersLine, String separator, String quotingMarker) {
		String splittingRegexp = WHITE_CHARS + separator + WHITE_CHARS;
		String[] splitted = parametersLine.trim().split(splittingRegexp, NEGATIVE);
		for (int i = 0; i < splitted.length; i++) {
			splitted[i] = transformParameter(splitted[i], quotingMarker);
		}
		return splitted;
	}

	String transformParameter(String trimmedInput, String quotingMarker) {
		boolean isNullExpected = "null".equalsIgnoreCase(trimmedInput);
		return isNullExpected ? null : removeEdges(trimmedInput, quotingMarker);
	}
	
	String removeEdges(String input, String toRemove) {
		String frontRemoved = StringUtils.removeStart(input, toRemove);
		return StringUtils.removeEnd(frontRemoved, toRemove);
	}
	
}

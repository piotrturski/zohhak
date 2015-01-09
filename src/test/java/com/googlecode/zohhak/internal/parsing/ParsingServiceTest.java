package com.googlecode.zohhak.internal.parsing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.Result;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.testutils.JUnitLauncher;

public class ParsingServiceTest {

	@Test
	public void should_report_parse_error_when_separator_is_invalid() {
		
		Result result = JUnitLauncher.runWithZohhak(IncorrectSeparatorSample.class);
		assertThat(result.getFailures().get(0).getException())
								.hasMessageContaining("annot parse parameters")
								.isInstanceOf(IllegalArgumentException.class);
	}
}

class IncorrectSeparatorSample {
	
	@TestWith(value ="a", separator="(")
	public void should_fail_because_of_wrong_regex_as_separator(String input) {}
}

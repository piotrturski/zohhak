package com.googlecode.zohhak.api;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class ParsingTest {

	@TestWith(value=" \t ")
	public void emptyString(String input) {
		assertThat(input).isEmpty();
	}
	
	@TestWith(" '' , a , ''b '  ")
	public void quotedAndNoQuoted(String empty, String a, String quoteB) {
		assertThat(empty).isEqualTo("");
		assertThat(a).isEqualTo("a");
		assertThat(quoteB).isEqualTo("'b ");
	}

	@TestWith(" '','' ")
	public void emptyStrings(String input1, String input2) {
		assertThat(input1).isEqualTo(input2).isEmpty();
	}
	
	@TestWith(value=" ' ' ", stringBoundary="")
	public void emptyBoundary(String input) {
		assertThat(input).isEqualTo("' '");
	}
	
	@TestWith(value=" ' ' ", stringBoundary="`")
	public void changedBoundary(String input) {
		assertThat(input).isEqualTo("' '");
	}
	
}

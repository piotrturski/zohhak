package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;

import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
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

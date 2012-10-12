package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;

import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
public class ParsingTest {

	@TestWith(" a ")
	public void trimTestSingle(String input) {
		assertThat(input).isEqualTo("a");
	}

	@TestWith(" a , b ")
	public void trimTestMulti(String a, String b) {
		assertThat(a).isEqualTo("a");
		assertThat(b).isEqualTo("b");
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
}

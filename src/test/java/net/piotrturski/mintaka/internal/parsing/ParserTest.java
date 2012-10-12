package net.piotrturski.mintaka.internal.parsing;

import static org.fest.assertions.api.Assertions.assertThat;

import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
public class ParserTest {

	Parser parser = new Parser(); 
	
	@Test
	public void testSplitOneElement() {
		String[] splited = parser.split(" a ");
		assertThat(splited).containsSequence("a").hasSize(1);
	}
	
	@Test
	public void testSplitMoreElements() {
		String[] splited = parser.split(" a , b ");
		assertThat(splited).containsSequence("a", "b").hasSize(2);
	}
	
	@Test
	public void testOneQuoted() {
		String[] splited = parser.split(" ' a ' ");
		assertThat(splited).containsSequence(" a ").hasSize(1);
	}
	
	@Test
	public void testTwoQuoted() {
		String[] splited = parser.split(" ' a ', ' b' ");
		assertThat(splited).containsSequence(" a ", " b").hasSize(2);
	}
	
	@TestWith({
		"aba,	b",
		"ba,	b",
		"aabaa,	aba",
		"b,		b",
		"a,		",
		" , ",
		","
	})
	public void removeEdgesTest(String input, String trimmed) {
		String withoutEdges = parser.removeEdges(input, "a");
		assertThat(withoutEdges).isEqualTo(trimmed);
	}
	
}

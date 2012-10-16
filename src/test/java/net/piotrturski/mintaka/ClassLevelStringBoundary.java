package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.runner.RunWith;

import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.runners.MintakaRunner;

@RunWith(MintakaRunner.class)
@Configure(stringBoundary="`")
public class ClassLevelStringBoundary {

	@TestWith(value=" `` a ' ")
	public void otherQuote(String input) {
		assertThat(input).isEqualTo("` a '");
	}
	
	@TestWith(value=" ' a ' ")
	public void noDefaultQuote(String input) {
		assertThat(input).isEqualTo("' a '");
	}

}
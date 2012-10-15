package net.piotrturski.mintaka.programmatic;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.TestWith;

@Configure(separator=";")
public class ClassLevelSplitter {

	@TestWith(value="7; 19")
	public void otherSeparator(int i, int j) {
		assertThat(i).isEqualTo(7);
		assertThat(j).isEqualTo(19);
	}
	
	@TestWith(value="7, 19")
	public void noDefaultSeparator(int i, int j) {
	}
	
	@TestWith(value="7 | 19", separator="\\|")
	public void locallyOverridenSeparator(int i, int j) {
		assertThat(i).isEqualTo(7);
		assertThat(j).isEqualTo(19);
	}
	
}

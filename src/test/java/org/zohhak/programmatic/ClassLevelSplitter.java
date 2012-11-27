package org.zohhak.programmatic;

import static org.fest.assertions.api.Assertions.assertThat;

import org.zohhak.Configure;
import org.zohhak.TestWith;

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

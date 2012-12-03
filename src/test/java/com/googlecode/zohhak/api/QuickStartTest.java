package com.googlecode.zohhak.api;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;

import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class QuickStartTest {

	@TestWith("c, 10, -13, 15, 1.5, 2.3, 7, true")
	public void coercingWrappers(Character c, Byte b, Short s, Integer i, Float f, Double d, Long l, Boolean bool) {
		assertThat(c).isEqualTo('c');
		assertThat(b).isEqualTo((byte)10);
		assertThat(s).isEqualTo((short)-13);
		assertThat(i).isEqualTo(15);
		assertThat(f).isEqualTo(1.5f, offset(0.0001f));
		assertThat(d).isEqualTo(2.3, offset(0.0001));
		assertThat(l).isEqualTo(7);
		assertThat(bool).isTrue();
	}
	
	
	
}

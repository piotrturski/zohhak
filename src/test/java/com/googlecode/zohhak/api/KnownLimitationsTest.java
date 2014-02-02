package com.googlecode.zohhak.api;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.runners.ZohhakRunner;

/**
 * this test shows some undesired behavior but unfortunately that's how it works
 */
@RunWith(ZohhakRunner.class)
public class KnownLimitationsTest {

	@TestWith("whatever")
	public void should_ignore_generics(List<Thread> list) {
		
		Object listElement = list.get(0);
		
		assertThat(listElement).isInstanceOf(Integer.class);
	}
	
	@Coercion
	public List<? extends Number> toList(String input) {
		return singletonList(4);
	}
	
}

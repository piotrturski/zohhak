package com.googlecode.zohhak.api;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;


@RunWith(ZohhakRunner.class)
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
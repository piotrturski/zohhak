package com.googlecode.zohhak.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class UserCoercionTest {

	
	@TestWith("0xFF, 19")
	public void should_coerce_hex_and_decimal(int hex, int decimal) {
		
		assertThat(hex).isEqualTo(255);
		assertThat(decimal).isEqualTo(19);
	}

	@TestWith("EN")
	public void should_coerce_locale(Locale locale) {
		
		assertThat(locale).isEqualTo(Locale.ENGLISH);
	}
	
	@Coercion
	public int toIntFromHex(String hexInt) {
		assertThat(hexInt).startsWith("0x");
		String plainHex = hexInt.substring(2);
		return Integer.parseInt(plainHex, 16);
	}
	
	@Coercion
	public Locale toLocale(String language) {
		return new Locale(language);
	}
}

package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
public class CoercingTest {
	
	@TestWith("ala")
	public void coerceString(String param) {
		assertThat(param).isEqualTo("ala");
	}
	
	@TestWith("7")
	public void coerceInt(int param) {
		assertThat(param).isEqualTo(7);
	}
	
	@TestWith("11")
	public void coerceInteger(Integer param) {
		assertThat(param).isEqualTo(11);
	}
	
	@TestWith("nuLl")
	public void coerceNull(Integer param) {
		assertThat(param).isNull();
	}
	
	@TestWith("7")
	public void coerceLong(Long param) {
		assertThat(param).isEqualTo(7);		
	}
	
	@TestWith("6")
	public void coerceObject(Object param) {
		assertThat(param).isInstanceOf(String.class).isEqualTo("6");
	}
	
	@TestWith("ala")
	public void coerceCharSequence(CharSequence param) {
		assertThat(param).isInstanceOf(CharSequence.class).isEqualTo("ala");
	}
	
	@Ignore
	@TestWith("0xFF")
	public void coerceHex(int param) {
		assertThat(param).isEqualTo(255);		
	}
}

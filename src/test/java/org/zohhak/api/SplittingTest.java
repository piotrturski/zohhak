package org.zohhak.api;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.zohhak.api.TestWith;
import org.zohhak.api.runners.ZohhakRunner;
import org.zohhak.helper.SampleEnum;

@RunWith(ZohhakRunner.class)
public class SplittingTest {

	@TestWith("7, 19")
	public void twoParams(int i, int j) {
		assertThat(i).isEqualTo(7);
		assertThat(j).isEqualTo(19);
	}

	@TestWith("7, LAST_VALUE")
	public void differentParamTypes(int i, SampleEnum j) {
		assertThat(i).isEqualTo(7);
		assertThat(j).isEqualTo(SampleEnum.LAST_VALUE);
	}
	
	@TestWith(value="7 | 19", separator="\\|")
	public void otherSeparator(int i, int j) {
		assertThat(i).isEqualTo(7);
		assertThat(j).isEqualTo(19);
	}
	
	@TestWith(value="7 | 19, 23", separator="[\\|,]")
	public void mixedSeparators(int i, int j, int k) {
		assertThat(i).isEqualTo(7);
		assertThat(j).isEqualTo(19);
		assertThat(k).isEqualTo(23);
	}
	
	@TestWith(value=" 7 = 7 > 5 => true", separator="=>")
	public void multiCharSeparator(String string, boolean bool) {
		assertThat(string).isEqualTo("7 = 7 > 5");
		assertThat(bool).isTrue();
	}
	
	@TestWith({
		" , ",
		" ,",
		", ",
		","
	})
	public void emptyStrings(String empty1, String empty2) {
		assertThat(empty1).isEqualTo(empty2).isEmpty();
	}
	
	@TestWith(" a ")
	public void trimTestSingle(String input) {
		assertThat(input).isEqualTo("a");
	}

	@TestWith(" a , b ")
	public void trimTestMulti(String a, String b) {
		assertThat(a).isEqualTo("a");
		assertThat(b).isEqualTo("b");
	}

	@TestWith("1")
	@Ignore
	public void varargs1(int... ints) {
		assertThat(ints).startsWith(1).hasSize(1);
	}
	
}

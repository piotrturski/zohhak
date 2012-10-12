package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.helper.SampleEnum;
import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.Ignore;
import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
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
	
	@TestWith("7 | 19")
	public void otherSeparator(int i, int j) {
		assertThat(i).isEqualTo(7);
		assertThat(j).isEqualTo(19);
	}
	
	@TestWith("7 | 19, 23")
	public void mixedSeparators(int i, int j, int k) {
		assertThat(i).isEqualTo(7);
		assertThat(j).isEqualTo(19);
		assertThat(k).isEqualTo(23);
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
	
	@TestWith("1")
	@Ignore
	public void varargs1(int... ints) {
		assertThat(ints).startsWith(1).hasSize(1);
	}
	
}

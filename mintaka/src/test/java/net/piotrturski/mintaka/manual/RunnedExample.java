package net.piotrturski.mintaka.manual;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import net.piotrturski.mintaka.MintakaRunner;
import net.piotrturski.mintaka.TestWith;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
public class RunnedExample {

	@Before public void initParams() {
    }
	
	@Test
	public void methodToRun() {}
	
	public void method2() {}
	
	@Test
	public void method3() {
		fail();
	}
	
	@Test @Ignore public void ignored() {} 

	@TestWith("1")
	public void methodWithParam(int param){
		assertThat(param).isEqualTo(1);
	}
}

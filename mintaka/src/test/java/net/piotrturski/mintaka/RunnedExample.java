package net.piotrturski.mintaka;

import org.junit.Before;
import org.junit.Rule;
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
	public void method3() {}

	@TestWith("1")
	public void methodWithParam(int param){}
}

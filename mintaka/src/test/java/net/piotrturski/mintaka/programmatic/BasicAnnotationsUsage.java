package net.piotrturski.mintaka.programmatic;

import static org.junit.Assert.fail;
import net.piotrturski.mintaka.MintakaRunner;
import net.piotrturski.mintaka.TestWith;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test is not supposed to be run on its own (under IDE or maven). in such case it will fail. 
 * it's used by other tests to check
 * if integration with JUnit (annotation discovery, method invocation, reporting etc.) is working properly.
 *
 */
@RunWith(MintakaRunner.class)
public class BasicAnnotationsUsage {

	@Before public void initParams() {}
	
	public void method2() {}

	@Test public void methodToRun() {}
	
	@Test public void method3() {
		fail();
	}
	
	@TestWith({"1", "2",})
	public void methodWithParam(int param){}

	@Test @Ignore public void ignored() {} 
	
	@Ignore @TestWith({"2", "1"})
	public void parametrizedIgnored(int i) {} 
}

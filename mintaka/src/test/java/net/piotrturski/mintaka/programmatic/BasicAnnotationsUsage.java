package net.piotrturski.mintaka.programmatic;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import net.piotrturski.mintaka.MintakaRunner;
import net.piotrturski.mintaka.ProgrammaticRunTest;
import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.ide.EclipseIntegration;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test is not supposed to be run on its own (under IDE or maven). 
 * it's hacked not to fail in such case (just for convenience). it has no value when run directly from under an IDE.
 *  
 * this class is used by other tests to check
 * if integration with JUnit (annotation discovery, method invocation, reporting etc.) is working properly.
 *
 */
@RunWith(MintakaRunner.class)
public class BasicAnnotationsUsage {

	@Before public void initParams() {}
	
	public void method2() {}

	@Test public void methodToRun() {}
	
	@Test public void method3() {
		failFrom(EclipseIntegration.class, ProgrammaticRunTest.class);
	}
	
	@TestWith({"1", "2"})
	public void methodWithParam(int param){}

	@Test @Ignore public void ignored() {} 
	
	@Ignore @TestWith({"2", "1"})
	public void parametrizedIgnored(int i) {}
	
	/**
	 * this method causes test to fail but only when invoked by specific classes. it prevents this class from failing when
	 * run directly from under an IDE (together with all other test classes).
	 * 
	 * @param classes
	 */
	private void failFrom(Class<?>... classes) {
		Set<String> classesOnStackTrace = new HashSet<String>();
		for (StackTraceElement s : Thread.currentThread().getStackTrace()) {
			classesOnStackTrace.add(s.getClassName());
		}
		for (Class<?> clazz : classes) {
			if (classesOnStackTrace.contains(clazz.getCanonicalName())) {
				fail();
			}
		}
	}
}

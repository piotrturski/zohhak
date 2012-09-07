package net.piotrturski.mintaka;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;


public class JunitRunTest {

	@Test
	public void runJunit() {
		Result runResult = JUnitCore.runClasses(RunnedExample.class);
		assertThat(runResult.getRunCount()).isEqualTo(1);
	}
	
}

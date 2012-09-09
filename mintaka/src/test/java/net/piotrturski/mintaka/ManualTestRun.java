package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;

import net.piotrturski.mintaka.manual.RunnedExample;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;


public class ManualTestRun {

	@Test
	public void runJunit() {
		Result runResult = JUnitCore.runClasses(RunnedExample.class);
		assertThat(runResult.getFailureCount()).isEqualTo(1);
		assertThat(runResult.getIgnoreCount()).isEqualTo(1);
		assertThat(runResult.getRunCount()).isEqualTo(3);
	}
	
}

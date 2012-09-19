package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;

import net.piotrturski.mintaka.programmatic.BasicAnnotationsUsage;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;


public class ProgrammaticRunTest {

	@Test
	public void runJunit() {
		Result runResult = JUnitCore.runClasses(BasicAnnotationsUsage.class);
		assertThat(runResult.getFailureCount()).isEqualTo(1);
		assertThat(runResult.getIgnoreCount()).isEqualTo(3);
		assertThat(runResult.getRunCount()).isEqualTo(4);
	}
	
}

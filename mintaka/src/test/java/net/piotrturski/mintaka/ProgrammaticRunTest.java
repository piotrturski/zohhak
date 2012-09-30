package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.helper.SampleType;
import net.piotrturski.mintaka.programmatic.BadParameterProcessing;
import net.piotrturski.mintaka.programmatic.BasicAnnotationsUsage;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;


public class ProgrammaticRunTest {

	@Test
	public void runJunit() {
		Result runResult = JUnitCore.runClasses(BasicAnnotationsUsage.class);
		assertThat(runResult.getFailureCount()).isEqualTo(1);
		assertThat(runResult.getIgnoreCount()).isEqualTo(3);
		assertThat(runResult.getRunCount()).isEqualTo(4);
	}
	
	@Test
	public void badParams() {
		Result result = runClassWithForcedRunner(BadParameterProcessing.class);
		
		assertThat(result.getRunCount()).isEqualTo(1);
		assertThat(result.getFailures()).hasSize(1);

		Failure failure = result.getFailures().get(0);
		String testLabel = failure.getTestHeader();
		Throwable exception = failure.getException();
		
		assertThat(testLabel)
						.doesNotContain("initializationError")
						.containsIgnoringCase("wrongCoercion");
		assertThat(exception)
						.hasMessageContaining("annot interpret string")
						.hasMessageContaining(SampleType.class.getCanonicalName())
						.isInstanceOf(IllegalArgumentException.class);
	}
	
	static public Result runClassWithForcedRunner(Class<?> clazz) {
		try {
			RunNotifier notifier = new RunNotifier();
			
			Result result= new Result();
			RunListener listener= result.createListener();
			notifier.addListener(listener);
			Runner runner = new MintakaRunner(clazz);
			runner.run(notifier);
			return result;
			
		} catch (InitializationError e) {
			throw new RuntimeException(e);
		}
	}
	
}

package net.piotrturski.mintaka.ide;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.MintakaRunner;
import net.piotrturski.mintaka.programmatic.BasicAnnotationsUsage;
import net.piotrturski.mintaka.programmatic.StandardTest;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public class EclipseIntegration {

	@Test
	public void classRequestFromSource() {
		Request request = Request.classes(BasicAnnotationsUsage.class);
		Result result = executeRequest(request);
		
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.getIgnoreCount()).isEqualTo(3);
		assertThat(result.getRunCount()).isEqualTo(4);
	}
	
	@Test
	public void methodRequestFromSource() {
		Result result = executeRequest(BasicAnnotationsUsage.class, "methodWithParam");
		
		assertThat(result.getFailures()).isEmpty();
		assertThat(result.getRunCount()).isEqualTo(2);
	}

	@Test
	public void methodRerunFromTestResultWindow() {
		Result result = executeRequest(BasicAnnotationsUsage.class, "methodWithParam [2]");
		
		assertThat(result.getFailures()).isEmpty();
		assertThat(result.getRunCount()).isEqualTo(1);
	}
	
	@Test
	public void checkDescription() throws InitializationError {
		Runner runner = new MintakaRunner(BasicAnnotationsUsage.class);
		Description description = runner.getDescription();
		
		assertThat(description.getChildren()).hasSize(5);
	}
	
	@Test
	public void learningBlockRunnerWithMethod() {
		Result result = executeRequest(StandardTest.class, "method1");
		
		assertThat(result.getFailures()).isEmpty();
		assertThat(result.getRunCount()).isEqualTo(1);
	}

	@Test
	public void learningBlockRunnerWithClass() {
		Request request = Request.classes(StandardTest.class);
		Result result = executeRequest(request);
		
		assertThat(result.getFailures()).isEmpty();
		assertThat(result.getRunCount()).isEqualTo(2);
	}
	
	private Result executeRequest(final Class<?> clazz, String methodName) {
		return executeRequest(createRequest(clazz, methodName));
	}
	
	private Result executeRequest(Request request) {
		RunNotifier notifier = new RunNotifier();

		Result result= new Result();
		RunListener listener= result.createListener();
		notifier.addListener(listener);
		
		Runner runner = request.getRunner();
		runner.run(notifier);
		return result;
	}
	
	private Request createRequest(final Class<?> clazz, String methodName) {
		Description method= Description.createTestDescription(clazz, methodName);
		return Request.classWithoutSuiteMethod(clazz).filterWith(method);
	}
	
}

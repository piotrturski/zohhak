package org.zohhak.ide;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.zohhak.api.runners.ZohhakRunner;
import org.zohhak.programmatic.BasicAnnotationsUsage;
import org.zohhak.programmatic.StandardTest;

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
		Runner runner = new ZohhakRunner(BasicAnnotationsUsage.class);
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
	
	public static Result executeClass(Class<?> clazz) {
		Request request = Request.classes(clazz);
		return executeRequest(request);
	}
	
	private Result executeRequest(final Class<?> clazz, String methodName) {
		return executeRequest(createRequest(clazz, methodName));
	}
	
	private static Result executeRequest(Request request) {
		RunNotifier notifier = new RunNotifier();

		Result result= new Result();
		RunListener listener= result.createListener();
		notifier.addListener(listener);
		
		Runner runner = request.getRunner();
		runner.run(notifier);
		return result;
	}
	
	static public Request createRequest(final Class<?> clazz, String methodName) {
		Description method= Description.createTestDescription(clazz, methodName);
		return Request.classWithoutSuiteMethod(clazz).filterWith(method);
	}
	
}

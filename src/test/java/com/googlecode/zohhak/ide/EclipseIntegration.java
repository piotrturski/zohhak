package com.googlecode.zohhak.ide;

import static com.googlecode.zohhak.testutils.JUnitLauncher.runJUnitRequest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;

import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.googlecode.zohhak.programmatic.BasicAnnotationsUsage;
import com.googlecode.zohhak.programmatic.StandardTest;

public class EclipseIntegration {

	@Test
	public void classRequestFromSourceAndRerunFromTestResultWindow() {
		Result result = executeEclipseRequest(BasicAnnotationsUsage.class);
		
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.getIgnoreCount()).isEqualTo(3);
		assertThat(result.getRunCount()).isEqualTo(4);
	}
	
	@Test
	public void methodRequestFromSource() {
		Result result = executeEclipseRequest(BasicAnnotationsUsage.class, "methodWithParam");
		
		assertThat(result.getFailures()).isEmpty();
		assertThat(result.getRunCount()).isEqualTo(2);
	}

	@Test
	public void methodRerunFromTestResultWindow() {
		Result result = executeEclipseRequest(BasicAnnotationsUsage.class, "methodWithParam [2]");
		
		assertThat(result.getFailures()).isEmpty();
		assertThat(result.getRunCount()).isEqualTo(1);
	}
	
	@Test
	public void should_fail_when_nonexisting_parameter_requested() {
		Result result = executeEclipseRequest(BasicAnnotationsUsage.class, "methodWithParam [nonexisting]");
		
		assertThatOnlyMethodNotFound(result);
	}
	
	@Test
	public void should_fail_when_nonexisting_method_requested() {
		Result result = executeEclipseRequest(BasicAnnotationsUsage.class, "nonExistingMethod [2]");
		
		assertThatOnlyMethodNotFound(result);
	}
	
	@Test
	public void checkDescription() throws InitializationError {
		Runner runner = new ZohhakRunner(BasicAnnotationsUsage.class);
		Description description = runner.getDescription();
		
		assertThat(description.getChildren()).hasSize(5);
	}
	
	@Test
	public void learningBlockRunnerWithMethod() {
		Result result = executeEclipseRequest(StandardTest.class, "method1");
		
		assertThat(result.getFailures()).isEmpty();
		assertThat(result.getRunCount()).isEqualTo(1);
	}

	@Test
	public void learningBlockRunnerWithClass() {
		Result result = executeEclipseRequest(StandardTest.class);
		
		assertThat(result.getFailures()).isEmpty();
		assertThat(result.getRunCount()).isEqualTo(2);
	}
	
	private static Result executeEclipseRequest(Class<?> clazz) {
		Request request = eclipseClassRequest(clazz);
		return runJUnitRequest(request);
	}
	
	private static Result executeEclipseRequest(Class<?> clazz, String methodName) {
		Request request = eclipseMethodRequest(clazz, methodName);
		return runJUnitRequest(request);
	}

	/**
	 *  the way eclipse creates request 
	 */
	private static Request eclipseClassRequest(Class<?> clazz) {
		return Request.classes(clazz);
	}
	
	/**
	 *  the way eclipse creates request 
	 */
	private static Request eclipseMethodRequest(Class<?> clazz, String methodName) {
		Description method = Description.createTestDescription(clazz, methodName);
		return Request.classWithoutSuiteMethod(clazz).filterWith(method);
	}
	
	
	private void assertThatOnlyMethodNotFound(Result result) {
		assertThat(result.getRunCount()).isEqualTo(1);
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.getFailures().get(0).getTestHeader())
									.containsIgnoringCase("initializationError");
		assertThat(result.getFailures().get(0).getMessage()).containsIgnoringCase("no tests found");
	}
}

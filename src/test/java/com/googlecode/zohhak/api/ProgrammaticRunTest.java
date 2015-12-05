package com.googlecode.zohhak.api;

import static com.googlecode.zohhak.testutils.JUnitLauncher.runWithZohhak;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.googlecode.zohhak.helper.SampleType;
import com.googlecode.zohhak.programmatic.BadParameterProcessing;
import com.googlecode.zohhak.programmatic.BasicAnnotationsUsage;
import com.googlecode.zohhak.programmatic.ClassLevelConfiguration;
import com.googlecode.zohhak.programmatic.ClassLevelSplitter;
import com.googlecode.zohhak.programmatic.MixedCoercers;
import com.googlecode.zohhak.testutils.JUnitLauncher;


public class ProgrammaticRunTest {

	@Test
	public void runJunit() {
		Result runResult = runWithZohhak(BasicAnnotationsUsage.class);
		assertThat(runResult.getFailureCount()).isEqualTo(1);
		assertThat(runResult.getIgnoreCount()).isEqualTo(3);
		assertThat(runResult.getRunCount()).isEqualTo(4);
	}
	
	@Test
	public void coercionNotFound() {
		Failure failure = coercionFailureFromSingleMethodExecution(BadParameterProcessing.class, "typeWithoutCoercion");
		Throwable exception = failure.getException();
		
		assertThat(exception).hasMessageContaining(SampleType.class.getCanonicalName());
	}

	@Test
	public void wrongBoolFormat() {
		Failure failure = coercionFailureFromSingleMethodExecution(BadParameterProcessing.class, "wrongBooleanFormat");
		assertThat(failure.getException()).hasMessageContaining("boolean");
	}
	
	@Test
	public void should_fail_when_wrong_char_format() {
		Failure failure = coercionFailureFromSingleMethodExecution(BadParameterProcessing.class, "wrongCharFormat");
		assertThat(failure.getException()).hasMessageContaining("char");
	}

	@Test
	public void wrongEnumFormat() {
		Failure failure = coercionFailureFromSingleMethodExecution(BadParameterProcessing.class, "wrongEnumFormat");
		assertThat(failure.getException()).hasMessageContaining("SampleEnum");
	}
	
	@Test
	public void tooManyParameters() {
		Result result = runWithZohhak(BadParameterProcessing.class, "tooManyParameters");
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.getRunCount()).isEqualTo(1);
		assertThat(result.getFailures().get(0).getException()).
										hasMessageContaining("1 parameter(s) declared but provided 2");
	}

	@Test
	public void tooFewParameters() {
		Result result = runWithZohhak(BadParameterProcessing.class, "tooFewParameters");
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.getRunCount()).isEqualTo(1);
		assertThat(result.getFailures().get(0).getException()).
										hasMessageContaining("2 parameter(s) declared but provided 1");
	}
	
	@Test
	public void coercerCache() {
		Result result = runWithZohhak(MixedCoercers.class);
		assertThat(result.getFailureCount()).isEqualTo(2);
		assertThat(result.getRunCount()).isEqualTo(3);
	}		
	
	@Test
	public void classLevelConfiguration() {
		Result result = JUnitLauncher.runWithZohhak(ClassLevelConfiguration.class, "samleType");
		assertThat(result.getFailureCount()).isEqualTo(0);
		assertThat(result.getRunCount()).isEqualTo(1);
	}
	
	@Test
	public void classLevelSplitter() {
		Result result = runWithZohhak(ClassLevelSplitter.class);
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.getRunCount()).isEqualTo(3);
		
		assertThat(result.getFailures().get(0).getDescription().getMethodName()).contains("noDefaultSeparator");
	}
	
	static private Failure coercionFailureFromSingleMethodExecution(Class<?> classToTest, String methodToTest) {
		Result result = runWithZohhak(classToTest, methodToTest);
		
		assertThat(result.getRunCount()).isEqualTo(1);
		assertThat(result.getFailures()).hasSize(1);

		Failure failure = result.getFailures().get(0);
		assertThat(failure.getTestHeader())
								.doesNotContain("initializationError")
								.contains(methodToTest);
							
		assertThat(failure.getException())
							.hasMessageContaining("annot interpret string")
							.isInstanceOf(IllegalArgumentException.class);
		return failure;
	}
	
}

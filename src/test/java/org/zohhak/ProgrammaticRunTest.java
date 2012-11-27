package org.zohhak;

import static org.fest.assertions.api.Assertions.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.zohhak.helper.SampleType;
import org.zohhak.programmatic.BadParameterProcessing;
import org.zohhak.programmatic.BasicAnnotationsUsage;
import org.zohhak.programmatic.ClassLevelConfiguration;
import org.zohhak.programmatic.ClassLevelSplitter;
import org.zohhak.programmatic.MixedCoercers;
import org.zohhak.runners.MintakaRunner;


public class ProgrammaticRunTest {

	@Test
	public void runJunit() {
		Result runResult = JUnitCore.runClasses(BasicAnnotationsUsage.class);
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
	public void wrongEnumFormat() {
		Failure failure = coercionFailureFromSingleMethodExecution(BadParameterProcessing.class, "wrongEnumFormat");
		assertThat(failure.getException()).hasMessageContaining("SampleEnum");
	}
	
	@Test
	public void tooManyParameters() {
		Result result = runClassWithForcedRunner(BadParameterProcessing.class, "tooManyParameters");
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.getRunCount()).isEqualTo(1);
		assertThat(result.getFailures().get(0).getException()).
										hasMessageContaining("1 parameter(s) declared but provided 2");
	}
	
	@Test
	public void tooFewParameters() {
		Result result = runClassWithForcedRunner(BadParameterProcessing.class, "tooFewParameters");
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.getRunCount()).isEqualTo(1);
		assertThat(result.getFailures().get(0).getException()).
										hasMessageContaining("2 parameter(s) declared but provided 1");
	}
	
	@Test
	public void coercerCache() {
		Result result = runClassWithForcedRunner(MixedCoercers.class, null);
		assertThat(result.getFailureCount()).isEqualTo(2);
		assertThat(result.getRunCount()).isEqualTo(3);
	}		
	
	@Test
	public void classLevelConfiguration() {
		Result result = runClassWithForcedRunner(ClassLevelConfiguration.class, "samleType");
		assertThat(result.getFailureCount()).isEqualTo(0);
		assertThat(result.getRunCount()).isEqualTo(1);
	}
	
	@Test
	public void classLevelSplitter() {
		Result result = runClassWithForcedRunner(ClassLevelSplitter.class, null);
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.getRunCount()).isEqualTo(3);
		
		assertThat(result.getFailures().get(0).getDescription().getMethodName()).contains("noDefaultSeparator");
	}
	
	static private Failure coercionFailureFromSingleMethodExecution(Class<?> classToTest, String methodToTest) {
		Result result = runClassWithForcedRunner(classToTest, methodToTest);
		
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
	
	
	static public Result runClassWithForcedRunner(Class<?> clazz, String methodName) {
		try {
			RunNotifier notifier = new RunNotifier();
			
			Result result= new Result();
			RunListener listener= result.createListener();
			notifier.addListener(listener);
			
			Request request = Request.runner(new MintakaRunner(clazz));
			if (StringUtils.isNotBlank(methodName)) { 
				Description methodDescription = Description.createTestDescription(clazz, methodName);
				request = request.filterWith(methodDescription);
			}
			Runner runner = request.getRunner();
			runner.run(notifier);
			return result;
			
		} catch (InitializationError e) {
			throw new RuntimeException(e);
		}
	}
	
}

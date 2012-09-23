package net.piotrturski.mintaka.ide;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.MintakaRunner;
import net.piotrturski.mintaka.programmatic.BasicAnnotationsUsage;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Result;
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
		Request request = createRequest(BasicAnnotationsUsage.class, "methodWithParam");
		Result result = executeRequest(request);
		
		System.out.println(result.getFailures());
		
		assertThat(result.getRunCount()).isEqualTo(2);
	}

	@Test
	public void checkDescription() throws InitializationError {
		MintakaRunner runner = new MintakaRunner(BasicAnnotationsUsage.class);
		Description description = runner.getDescription();
		
		assertThat(description.getChildren()).hasSize(5);
	}
	
	private Result executeRequest(Request request) {
		RunNotifier notifier = new RunNotifier();

		Result result= new Result();
		RunListener listener= result.createListener();
		notifier.addListener(listener);
		
		request.getRunner().run(notifier);
		return result;
	}
	
	private Request createRequest(final Class<?> clazz, String methodName) {
		Description method= Description.createTestDescription(clazz, methodName);
		return Request.classWithoutSuiteMethod(clazz).filterWith(method);
	}
	
}

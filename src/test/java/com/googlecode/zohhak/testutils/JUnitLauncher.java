package com.googlecode.zohhak.testutils;

import static org.junit.runner.Description.createTestDescription;

import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.mockito.Mockito;

import com.googlecode.zohhak.api.runners.ZohhakRunner;

public class JUnitLauncher {

	private static final String ALL_METHODS = null;

	public static Result runWithZohhak(Class<?> clazz) {
		return runWithZohhak(clazz, ALL_METHODS);
	}

	public static Result runWithZohhak(Class<?> requestedClazz, String methodName) {
		final Class<?> clazz = createClassWithPublicConstructor(requestedClazz);
		final Request classRequest = createJUnitClassRequest(clazz);
		final Request finalRequest = methodName != ALL_METHODS ? 
								classRequest.filterWith(createTestDescription(clazz, methodName)) 
								: classRequest;
		return runJUnitRequest(finalRequest);
	}

	public static Result runJUnitRequest(Request request) {
		RunNotifier notifier = new RunNotifier();
		Result result = new Result();
		RunListener listener = result.createListener();
		notifier.addListener(listener);
		Runner runner = request.getRunner();
		runner.run(notifier);
		return result;
	}

	private static Request createJUnitClassRequest(Class<?> clazz) {
		try {
			return Request.runner(new ZohhakRunner(clazz));
		} catch (InitializationError e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * creates copy of a class but with public constructor. it makes non-public
	 * classes with default constructor testable. useful for testing
	 */
	private static Class<?> createClassWithPublicConstructor(Class<?> clazz) {
		return Mockito.mock(clazz).getClass();
	}
}

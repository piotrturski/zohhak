package com.googlecode.zohhak.internal.junit;

import java.lang.annotation.Annotation;

import org.junit.runners.model.FrameworkMethod;

import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.TestWith;


public class AnnotationUtil {

	public static String[] getParameters(FrameworkMethod method) {
		TestWith testWith = method.getAnnotation(TestWith.class);
		if (testWith != null) {
			return testWith.value();
		}
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			Configure configure = annotation.annotationType().getAnnotation(Configure.class);
			if (configure != null) {
				
			}
		}
		return null;
	}
	
}

package net.piotrturski.mintaka.internal.junit;

import java.lang.annotation.Annotation;

import org.junit.runners.model.FrameworkMethod;

import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.TestWith;

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

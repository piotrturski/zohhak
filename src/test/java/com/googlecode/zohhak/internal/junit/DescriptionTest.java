package com.googlecode.zohhak.internal.junit;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class DescriptionTest {

    @Rule
    public TestRule rule_asserting_that_description_contains_annotation = new TestRule() {
    	
        public Statement apply(Statement base, Description description) {
        	
        	assertThat(description.getAnnotation(SampleMethodAnnotation.class))
        										.as("annotation within description")
        										.isNotNull();
            return base;
        }
    };

    @SampleMethodAnnotation
    @TestWith("whatever")
    public void should_pass_annotations_inside_description(String arg) {}
    
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD)
    private static @interface SampleMethodAnnotation {}
}

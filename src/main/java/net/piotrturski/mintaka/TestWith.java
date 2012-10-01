package net.piotrturski.mintaka;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.piotrturski.mintaka.internal.DefaultCoercer;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestWith {

	String[] value();

	boolean split() default true;
	
	Class<?>[] coercer() default DefaultCoercer.class;
	
}

package net.piotrturski.mintaka.helper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.piotrturski.mintaka.Configure;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Configure(configuration=ConstantConfiguration.class)
public @interface CustomTest {

	String[] value();
	
}

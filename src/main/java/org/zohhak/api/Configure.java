package org.zohhak.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Configure {

	//Class<? extends Configuration> configuration() default Configuration.class;
	
	Class<?>[] coercer() default DefaultCoercer.class;
	
	boolean inheritCoercers() default true;
	
	String separator() default ConfigurationDefinition.INHERIT;
	
	String stringBoundary() default ConfigurationDefinition.INHERIT;
	
	Class<? extends Configuration> configuration() default Inherit.class;
}

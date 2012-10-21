package net.piotrturski.mintaka;

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
	
	String separator() default ConfigurationDefinition.DEFAULT_SEPARATOR_MARKER;
	
	String stringBoundary() default ConfigurationDefinition.DEFAULT_STRING_BOUNDARY_MARKER;
	
	Class<? extends Configuration> configuration() default InheritedConfiguration.class;
}

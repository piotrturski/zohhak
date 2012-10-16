package net.piotrturski.mintaka;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface TestWith {

	String[] value();

	boolean split() default true;

	Class<?>[] coercer() default DefaultCoercer.class;

	/**
	 * Optionally specify separator used to split parameters. It's a part of regexp so remember about escaping and
	 * special characters. If omitted, <code>","</code> (comma) is assumed. 
	 * 
	 * <pre>
	 * separator = ";"      -> ;
	 * separator = "\\|"    -> |
	 * separator = "[,;]"   -> , or ;
	 * separator = "=>"     -> =>
	 * </pre>
	 */
	String separator() default ConfigurationDefinition.DEFAULT_SEPARATOR_MARKER;
	
	String stringBoundary() default ConfigurationDefinition.DEFAULT_STRING_BOUNDARY_MARKER;

}

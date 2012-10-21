package net.piotrturski.mintaka;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface TestWith {

	String[] value();

	Class<?>[] coercer() default DefaultCoercer.class;

	/**
	 * Optionally specify separator used to split parameters. It's a part of regexp so remember about escaping and special characters. If
	 * omitted, <code>","</code> (comma) is assumed.
	 * 
	 * <pre>
	 * separator = ";"      -> ;
	 * separator = "\\|"    -> |
	 * separator = "[,;]"   -> , or ;
	 * separator = "=>"     -> =>
	 * </pre>
	 * 
	 * Separator \u0000 has special meaning and is not allowed
	 */
	String separator() default ConfigurationDefinition.DEFAULT_SEPARATOR_MARKER;

	/**
	 * Special string to mark end string boundary. Will be removed during parsing but any surrounded characters will be preserved.
	 * Default is <code>"'"</code> (single quotation mark).
	 * 
	 * <pre>
	 * stringBoundary   string before parse     string after parse
	 * "'"              "   abc     "           "abc"
	 * "'"              " ' abc '   "           " abc "
	 * "'"              " '' abc  ' "           "' abc  "
	 * "|"              " ' abc '   "           "' abc '"
	 * ""               " ' abc '   "           "' abc '"
	 * </pre>
	 * 
	 */
	String stringBoundary() default ConfigurationDefinition.DEFAULT_STRING_BOUNDARY_MARKER;

	Class<? extends Configuration> configuration() default InheritedConfiguration.class;

	boolean inheritCoercers() default true;

	// Class<? extends Configuration>
}

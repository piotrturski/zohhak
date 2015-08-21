### Hello world
```java
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class HelloWorldTest {

    @TestWith({
        "2, 1,   3",
        "3, 5,   8"
    })
    public void should_add_numbers(int addend1, int addend2, int result) {
		
        assertThat(addend1 + addend2).isEqualTo(result);
    }
}
```
This will run two tests: `should_add_numbers(2, 1, 3)` and `should_add_numbers(3, 5, 8)`. Each execution will be reported as an independent test.
### Out of the box
Some types are supported by default: primitives, wrappers, enums etc. Full list of those types is in [Basic usage](Full-Guide.md#basic-usage) section.
```java
@RunWith(ZohhakRunner.class)
public class CoercingTest {

    @TestWith("c, 10, -13, 15, 1.5, 2.3, 7, true")
    public void should_coerce_primitives(char c, byte b, short s, int i, 
                                         float f, double d, long l, boolean bool) {
        // works also with wrappers
        assertThat(c).isEqualTo('c');
        assertThat(b).isEqualTo((byte)10);
        assertThat(s).isEqualTo((short)-13);
        assertThat(i).isEqualTo(15);
        assertThat(f).isEqualTo(1.5f, offset(0.0001f));
        assertThat(d).isEqualTo(2.3, offset(0.0001));
        assertThat(l).isEqualTo(7);
        assertThat(bool).isTrue();
    }

    @TestWith("ONE_OF_ENUM_VALUES")
    public void should_coerce_enum(SampleEnum param) {
        assertThat(param).isEqualTo(SampleEnum.ONE_OF_ENUM_VALUES);		
    }

    @TestWith("null")
    public void should_coerce_null(WhateverType whateverType) {
        assertThat(whateverType).isNull();
    }

    @TestWith(" john doe , ' john doe ' ")
    public void should_coerce_string(String trimmed, String untrimmed) {
        assertThat(trimmed).isEqualTo("john doe");
        assertThat(untrimmed).isEqualTo(" john doe ");
    }

    @Test
    public void should_run_standard_junit_test() {
        //this will also work
    }
}
```
### Custom types
What if you want other type in your tests? Let's say the `Locale`.
```java
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Locale;
import org.junit.runner.RunWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class UserCoercionTest {	

    @TestWith("EN")
    public void should_coerce_locale(Locale locale) {
		
    	assertThat(locale).isEqualTo(Locale.ENGLISH);
    }
	
    @Coercion
    public Locale toLocale(String language) {
    	return new Locale(language);
    }
}
```
You can also change the 'out-of-the-box' behavior. Let's say we want to provide `int` not only as a decimal but also as a hex.
```java
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class UserCoercionTest {
	
    @TestWith("0xFF, 19")
    public void should_coerce_hex_and_decimal(int hex, int decimal) {
		
        assertThat(hex).isEqualTo(255);
        assertThat(decimal).isEqualTo(19);
    }

    @Coercion
    public int toIntFromHex(String hexInt) {
    	assertThat(hexInt).startsWith("0x");
        String plainHex = hexInt.substring(2);
        return Integer.parseInt(plainHex, 16);
    }
}
```
and many, many more. See the [Full Guide](Full-Guide.md).

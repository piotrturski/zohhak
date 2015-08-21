### Introduction
_This tutorial uses [AssertJ](http://joel-costigliola.github.io/assertj)_

Zohhak provides junit runner that allows you to create test methods with parameters. Each of those methods has an array of strings provided within annotation. Each of those strings represents all parameters in a single test method execution. The following method will be executed two times.
```java
@TestWith({
    "clerk,      45'000 USD, GOLD",
    "supervisor, 60'000 GBP, PLATINUM"
})
public void canAcceptDebit(Employee employee, Money money, ClientType clientType) {
    assertThat(...
}
```
Zohhak:
  1. takes each string (e.g. "clerk, 45'000 USD, GOLD")
  1. splits it (if necessary) to produce required number of parameters (e.g. "clerk", "45'000 USD", "GOLD")
  1. coerces each string to the corresponding method's parameter (e.g. Employee, Money, ClientType)
  1. executes method with produced parameters

Many aspects of splitting and coercing can be configured. Zohhak uses its default configuration which can be overridden with class and/or method level configurations.

### Basic usage
First of all check [Out of the box](Quick-Start.md#out-of-the-box) section in quick start. In case you are too lazy, a short snippet:
```java
@RunWith(ZohhakRunner.class)
public class CoercionTest {

    @TestWith("ONE_OF_ENUM_VALUES, 3, null, John Doe")
    public void coerce(SampleEnum enumValue, int intValue, WhateverType nullValue, String stringValue) {

	assertThat(enumValue).isEqualTo(SampleEnum.ONE_OF_ENUM_VALUES);		
        assertThat(intValue).isEqualTo(3);
        assertNull(nullValue)
        assertThat(stringValue).isEqualTo("John Doe");
    }
}
```

Zohhak by default supports:
  * primitives and their wrappers
  * nulls
  * enums
  * String
  * BigInteger, BigDecimal (since 1.1.0)

and types assignable from them (eg. Number, CharSequence, Object). Parameters are separated with comma, edge white characters are trimmed (unless apostrophes are used).

#### Commas, white chars, apostrophes
`@TestWith` contains parameter 'separator'. it's a regexp used to splitting parameters. The default value is "," (a comma) but can be changed
```java
@TestWith(value="7 | 19, 23", separator="[\\|,]")
public void mixedSeparators(int i, int j, int k) {
    assertThat(i).isEqualTo(7);
    assertThat(j).isEqualTo(19);
    assertThat(k).isEqualTo(23);
}
	
@TestWith(value=" 7 = 7 > 5 => true", separator="=>")
public void multiCharSeparator(String string, boolean bool) {
    assertThat(string).isEqualTo("7 = 7 > 5");
    assertThat(bool).isTrue();
}
```
For more details see [@TestWith](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/TestWith.java) javadoc or
[SplittingTest.java](https://github.com/piotrturski/zohhak/blob/master/src/test/java/com/googlecode/zohhak/api/SplittingTest.java)

Only edge white chars are trimmed so you can place them inside your parameters. Same applies to apostrophes (string boundaries) but at most one string boundary is removed from each side of parameter. And you can always change 'stringBoundary' parameter of `@TestWith` annotation. It's not a regexp, it's a string
```java
@TestWith(" ' abc' ")
public void defaultBoundaryBasics(String abc) {
    assertThat(abc).isEqualTo(" abc");
}

@TestWith(" '' abc  , '' ,  , friend's car ")
public void defaultBoundaryAdvanced(String abc, String empty1, String empty2, String car) {
    assertThat(abc).isEqualTo("' abc");
    assertThat(empty1).isEqualTo(empty2).isEmpty();
    assertThat(car).isEqualTo("friend's car");
}

@TestWith(value=" ' '  ", stringBoundary="")
public void noBoundary(String input) {
    assertThat(input).isEqualTo("' '");
}
```
For more details check [@TestWith](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/TestWith.java) javadoc and [ParsingTest.java](https://github.com/piotrturski/zohhak/blob/master/src/test/java/com/googlecode/zohhak/api/ParsingTest.java)

#### null
"null" (case insensitive) is converted to null of any type. currently there is no possibility to change pattern recognized as null;
```java
@TestWith({"null, NULL",
           "Null, nuLL"})
public void nullTest(WhateverType firstNull, AnotherType secondNull) {
    assertThat(firstNull).isEqualTo(secondNull).isNull();
}

@TestWith(" 'null' ")
public void notNullTest(String notNull) {
    assertThat(notNull).isEqualTo("null");
}
```
### Custom types
Zohhak always tries to coerce provided string to desired type (parameter's type). Coercions are used to achieve it. Coercion is a method that converts one object of one type to an object of another type. For convenience coercions may be grouped in coercer (pojo). By default Zohhak uses [DefaultCoercer](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/DefaultCoercer.java) that provides out-of-the-box coercions. If you want to use other types you have to provide additional coercions. To add support for user type `Money`, all you need is:
```java
public Money whateverName(String input) {
    return new Money(new BigDecimal(input));
}
```
You don't have to care about:
  * error handling - just throw any `Throwable` you want. including checked exceptions.
  * preparing input - edge white chars are trimmed, string boundaries removed
  * null checking - `input` will never be `null`
  * border case and null returning - if zohhak decides (based on input parameter) that `null` should be returned, your coercion will not be invoked at all. Of course, your coercion still may return `null` if you really need it.

If coercion fails (throws any `Throwable`), Zohhak will look for another matching coercion. You can also add as many coercions as you want, for any types you want. You can have many coercions for one type.

**Limitation:** coercions and coercers must be stateless. You can't make any assumptions on how many coercer instances will be created, how many threads will be executing the same coercion at the same time. it's an implementation detail.

#### Multiple coercions for a single type

**Limitation:** you cannot make any assumptions on order in which Zohhak will look for coercions. That means coercions for the same type should have separated domains.

Let's try to add support for handling integers in hex.
  1. We need to assure that default coercion will fail trying to parse that hex (boilerplate)
  1. We need to assure that new coercion will not parse decimal integers (boilerplate)
  1. Convert string to integer (business code)

First problem can be solved by choosing proper representation. If we write hex as 0x... then the default coercion will fail. Now second problem is easy - we have to throw exception when there is no "0x" in the beginning. Let's code:

```java
public int toIntFromHex(String hexInt) {
    assertThat(hexInt).startsWith("0x");
    String plainHex = hexInt.substring(2);
    return Integer.parseInt(plainHex, 16);
}
```
pff, easy. but where to place that code?

#### Registering coercions
To make zohhak uses your coercions, you have to register them. It can be done in a few ways (depending on how often you plan to use this particular coercion). The quickest way is to place coercion inside a test class and mark it as `@Coercion`

```java
@RunWith(ZohhakRunner.class)
public class CoercionTest {

    @TestWith({
        "12.456",
        "-3.4"
    })
    public void testMethod(Money money) {
        assertThat...
    }

    @Coercion
    public Money whateverName(String input) {
        return new Money(new BigDecimal(input));
    }
}
```
Of course, you don't have to copy/paste coercions to every test class. Another way is to create a coercer.
```java
public class MyCoercer {

    public Money whateverName(String input) {
        return new Money(new BigDecimal(input));
    }
}
```
You can have many coercions inside one coercer. Such coercer can be used in your tests
```java
@RunWith(ZohhakRunner.class)
public class CoercionTest {

    @TestWith(coercers=MyCoercer.class, value={
        "12.456",
        "-3.4"
    })
    public void testMoney(Money money) {
        assertThat...
    }
}
```

### DRYing configuration

Placing all the configuration in every `@TestWith` makes your tests less readable and forces you to copy/paste the code. Let's get rid of it.

#### Basic cascading

Processing of each zohhak's test method depends on configuration. Configuration can be placed on many different levels.

  1. Default configuration (specified by [DefaultConfiguration.java](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/DefaultConfiguration.java))
  1. `@Configure` at class level
  1. `@TestWith` at method level

Configuration with higher priority (greater number) has precedence over configuration with lower priority. Furthermore both `@TestWith` and `@Configure` contain parameter named 'configuration'. This parameter has lower priority than any other parameters within the same annotation. This allows to reduce amount of configuration. For each test in a class, the default configuration is used unless you provide class level @Configure. If you need to configure many classes then it's easier to create your own Configuration class instead of provide same set of parameters every time. But even if you provide your own configuration you can still tune configuration for one specific class or method because configuration has lower priority than other parameters within annotation. Same apply to method level configuration. To complicated? Let's see examples:
```java
@RunWith(ZohhakRunner.class)
public class TestClass {

    @TestWith("1, 2")                 
    public void method1(int i, int j) {}

    @TestWith("1; 2", separator=";") 
    public void method2(int i, int k) {}
}
```
```java
@RunWith(ZohhakRunner.class)
@Configure(separator=";")            // changing class separator
public class TestClass {

    @TestWith("1; 2")                   
    public void method1(int i, int j) {}

    @TestWith("1, 2", separator=",")    
    public void method2(int i, int k) {}
}
```
`stringBoundary` works exactly the same way. Coercers configuration will be discussed later. Now let's see configuration in action.
```java
public class SemicolonConfiguration extends DefaultConfiguration {

    @Override
    public String separator() {
        return ";";
    }
}
```
```java
@RunWith(ZohhakRunner.class)
@Configure(separator="\\|")
public class TestClass {

    @TestWith("1 | 2")                   
    public void method1(int i, int j) {}

    @TestWith("1; 2", configuration=SemicolonConfiguration.class)
    public void method2(int i, int j) {}

    @TestWith("1, 2", separator=",", configuration=SemicolonConfiguration.class)
    public void method3(int i, int k) {}
}
```
and the most concise (for current zohhak's version) form:
```java
@RunWith(ZohhakRunner.class)
@Configure(configuration=SemicolonConfiguration.class)
public class TestClass {

    @TestWith("1 ; 2")                   
    public void method1(int i, int j) {}

    @TestWith("1, 2", separator=",")
    public void method2(int i, int j) {}

    @TestWith("1, 2", configuration=CommaConfiguration.class)
    public void method3(int i, int k) {}
}
```

#### Coercers cascading

As you see above, `separator`, `stringBoundary` and `configuration` are inherited or overridden with new values. There is another parameter `coercers` that lets you provide a list of coercers. However those coercers will not override coercers defined before. They will be added to the inherited coercers. You can think of it as registering additional coercers.
```java
@RunWith(ZohhakRunner.class)
@Configure(coercers={Coercer1.class, Coercer2.class})
public class TestClass {

    @TestWith("1, 2")
    public void passMethod1(TypeFromCoercer1 param1, TypeFromCoercer2 param2) {}

    @TestWith(value="1, 2, 3", configuration=ConfigurationWithCoercer3)
    public void passMethod2(TypeFromCoercer1 param1, TypeFromCoercer2 param2, TypeFromCoercer3 param3) {}

    @TestWith(value="1, 2, 3", coercers=ConfigurationWithCoercer3)
    public void passMethod3(TypeFromCoercer1 param1, TypeFromCoercer2 param2, TypeFromCoercer3 param3) {}

    @TestWith("1")
    public void passMethod4(int i) {}
}
```
If you really need, you can to override (or deregister) inherited coercers. Just use `inheritCoercers=false`. What is also important `inheritCoercers` is not inherited itself. It's always `true` unless explicitly stated otherwise. You can think of it as deregistering inherited coercers.
```java
@RunWith(ZohhakRunner.class)
@Configure(coercers=Coercer1.class, inheritCoercers=false)   //reject default coercers
public class TestClass {

    @TestWith("1")
    public void passMethod1(TypeFromCoercer1 param1) {}

    @TestWith(value="1", inheritCoercers=false)              // reject Coercer1. so this will fail
    public void failMethod1(TypeFromCoercer1 param1) {}    

    @TestWith("1")
    public void failMethod2(int i) {}                        // this will fail because default coercers are rejected
}
```
You cannot deregister in-test coercions
```java
@RunWith(ZohhakRunner.class)
public class TestClass {
 
    @TestWith(value="1", inheritCoercers=false)                    // this will pass
    public void passMethod(CustomType param) {}

    @Coercion
    public CustomType toCustomType(String input) {...}
}
```
#### Inheriting configuration
When you write your own `Configuration` class, you don't have to set explicitly all the parameters. You can mark them as 'inherited'. In such case, marked parameters will not be changed by your configuration. All you have to do is simply set the parameter of your configuration to the special value. For details see [Inherit.java](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/Inherit.java), configuration that inherits everything (may be helpful when writing your own configurations).
#### Summary
Both `@TestWith` and `@Configure` have the following set of configuration parameters (for details check [@TestWith](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/TestWith.java) javadoc):

| **parameter**     | **description**                        | **`DefaultConfiguration` value** | **`@TestWith`/`@Configure` value** | **is inherited** |
|:------------------|:---------------------------------------|:---------------------------------|:-----------------------------------|:-----------------|
| separator         | regexp (string) used for splitting parameters | "," (comma)                      | [ConfigurationDefinition](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/ConfigurationDefinition.java).INHERIT | yes              |
| stringBoundary    | string used for explicit string        | "'" (apostrophe)                 | [ConfigurationDefinition](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/ConfigurationDefinition.java).INHERIT | yes              |
| coercers          | array of additional coercers (classes)                             | { [DefaultCoercer.class](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/DefaultCoercer.java) } | { } (empty array)                  | yes              |
| inheritCoercers   | boolean indicates if inherited coercers should also be used or not | false                            | true                               | no               |
| configuration     | class (that implements [Configuration](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/Configuration.java)) that stores all above parameters. Has lower priority than parameters provided explicitly | _N/A_                            | [Inherit.class](https://github.com/piotrturski/zohhak/blob/master/src/main/java/com/googlecode/zohhak/api/Inherit.java) | yes              |

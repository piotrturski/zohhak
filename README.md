# Zohhak for Jmockit annotations

[read main zohhak documentation](https://github.com/piotrturski/zohhak)
#### add @Capturing  without declare in @TestWith
by Defauly Zohhak don't allow you to use more parameters that defined in @TestWith annotation. you can do this in `zohhak for Jmockit` package like this without `throw` an `ArrayIndexOutOfBoundsException` Exception:
```java
@TestWith({
        "1, 1",
        "2, 2",
        "7, 7",
        "8, 8"
})
public void test_is_euqal(int input, int expected,@Capturing final Something x){
    Assert.assertEquals(expected, input);
}
```

#### add more annotation to the package
go to `internal/coercing/CoercingService.java` and change ` reservedAnnotations` variable for more annotations:  
#### default value:
```java
    private String[] reservedAnnotations = {
            "com.googlecode.zohhak.api.ignoreZohhak",
            "mockit.Capturing",
            "mockit.Mocked",
    };
```

#### use `@ignoreZohhak` also use for don't throwing Exception
you can use `ignoreZohhak` whenever you want to use more variable than default:
```java
@TestWith({
        "1, 1",
        "2, 2",
})
public void test_is_euqal(int input, int expected,@ignoreZohhak final Person p){
    System.out.println(p.getName());
    Assert.assertEquals(Integer.valueOf(expected), Integer.valueOf(input));
}
```

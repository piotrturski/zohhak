package com.googlecode.zohhak.internal.coercing;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.backend.ParameterCalculator;
import com.googlecode.zohhak.api.backend.executor.ParameterCalculatorProvider;
import org.junit.Test;

import java.lang.reflect.Method;

public class DefaultParameterCoercerTest {
    private ParameterCalculatorProvider provider = new ParameterCalculatorProvider();
    private ParameterCalculator executor = provider.getExecutor();

    @Test(expected = IllegalArgumentException.class)
    public void notSupportedType() throws NoSuchMethodException {
        Method method = getClass().getMethod("methodWithNotSupportedType", Thread.class);

        executor.calculateParameters("anything", method);
    }

    @Test(expected = IllegalArgumentException.class)
    public void notClass() throws Exception {
        Method method = getClass().getMethod("methodWithNonClassTypeParameter", Object.class);

        executor.calculateParameters("anything", method);
    }

    @TestWith("")
    @SuppressWarnings("unused")
    public <T> void methodWithNonClassTypeParameter(T parameter) {}

    @TestWith("")
    @SuppressWarnings("unused")
    public void methodWithNotSupportedType(Thread unsupportedType) {}

}
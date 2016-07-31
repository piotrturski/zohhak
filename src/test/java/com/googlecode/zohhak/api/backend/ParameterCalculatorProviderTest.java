package com.googlecode.zohhak.api.backend;

import com.googlecode.zohhak.api.ConfigurationDefinition;
import com.googlecode.zohhak.api.DefaultCoercer;
import com.googlecode.zohhak.api.backend.executor.ParameterCalculatorProvider;
import com.googlecode.zohhak.internal.coercing.DefaultParameterCoercerFactory;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParameterCalculatorProviderTest {

    private static final Object CUSTOM_COERCION = new Object();

    @Test
    public void executorIsProvided() {
        ParameterCalculatorProvider provider = new ParameterCalculatorProvider();

        assertThat(provider.getExecutor()).isNotNull();
    }

    @Test
    public void canProvideAdditionalConfiguration() throws NoSuchMethodException {
        ParameterCalculatorProvider provider = new ParameterCalculatorProvider();
        ParameterCalculator executor = provider.getExecutor(new DefaultParameterCoercerFactory(), new CustomConfigurationResolver());

        Method testMethod = getClass().getMethod("testThatRequiresAdditionalConfiguration", String.class, String.class);

        Object[] parameters = executor.calculateParameters("a;a", testMethod);

        assertArrayEquals(parameters, new String[]{"a", "a"});
    }

    @Test
    public void canProvideParameterCoercerFactory() throws NoSuchMethodException {
        ParameterCalculatorProvider provider = new ParameterCalculatorProvider();
        ParameterCalculator executor = provider.getExecutor(new CustomParameterCoercerFactory(), new CustomConfigurationResolver());

        Method testMethod = getClass().getMethod("testThatWillBeCalledWithACustomCoercerFactory", Object.class);

        Object[] parameters = executor.calculateParameters("a", testMethod);

        assertArrayEquals(parameters, new Object[]{CUSTOM_COERCION});
    }

    @SuppressWarnings("unused")
    public void testThatWillBeCalledWithACustomCoercerFactory(Object custom) {}

    @SuppressWarnings("unused")
    public void testThatRequiresAdditionalConfiguration(String first, String second) {
        assertEquals(first, second);
    }

    private class CustomParameterCoercerFactory implements ParameterCoercerFactory {
        public ParameterCoercer parameterCoercer(ParameterCoercer defaultParameterCoercer) {
            return new CustomParameterCoercer();
        }
    }

    private class CustomParameterCoercer implements ParameterCoercer {

        public Object coerceParameter(Type targetType, String stringToParse) {
            return CUSTOM_COERCION;
        }
    }


    private class CustomConfigurationResolver implements ConfigurationResolver {

        public ConfigurationBuilder calculateConfiguration(Method testMethod) {
            return new CustomConfigurationBuilder();
        }
    }

    private class CustomConfigurationBuilder implements ConfigurationBuilder {

        public List<Class<?>> getCoercers() {
            return Collections.<Class<?>>singletonList(DefaultCoercer.class);
        }

        public String getParameterSeparator() {
            return ";";
        }

        public String getStringBoundary() {
            return ConfigurationDefinition.DEFAULT_STRING_BOUNDARY;
        }
    }
}
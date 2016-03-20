package com.googlecode.zohhak.api.backend.executor;

import com.googlecode.zohhak.api.backend.ConfigurationResolver;
import com.googlecode.zohhak.api.backend.ParameterCalculator;
import com.googlecode.zohhak.api.backend.ParameterCoercerFactory;
import com.googlecode.zohhak.internal.ConfigLineConfigurationResolver;
import com.googlecode.zohhak.internal.IoCContainer;
import com.googlecode.zohhak.internal.coercing.DefaultParameterCoercerFactory;

public class ParameterCalculatorProvider {

    public ParameterCalculator getExecutor() {
        return getExecutor(new DefaultParameterCoercerFactory(), new ConfigLineConfigurationResolver());
    }

    public ParameterCalculator getExecutor(ParameterCoercerFactory parameterCoercerFactory, ConfigurationResolver configurationResolver) {
        return new IoCContainer(parameterCoercerFactory, configurationResolver).getExecutor();
    }
}

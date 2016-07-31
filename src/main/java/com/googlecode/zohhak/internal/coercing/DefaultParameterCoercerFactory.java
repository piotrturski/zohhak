package com.googlecode.zohhak.internal.coercing;

import com.googlecode.zohhak.api.backend.ParameterCoercer;
import com.googlecode.zohhak.api.backend.ParameterCoercerFactory;

public class DefaultParameterCoercerFactory implements ParameterCoercerFactory {

    public ParameterCoercer parameterCoercer(ParameterCoercer defaultParameterCoercer) {
        return defaultParameterCoercer;
    }
}

package com.googlecode.zohhak.api.backend;

public interface ParameterCoercerFactory {
    ParameterCoercer parameterCoercer(ParameterCoercer defaultParameterCoercer);
}

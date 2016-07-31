package com.googlecode.zohhak.api.backend;

import java.lang.reflect.Method;

public interface ParameterCalculator {
    Object[] calculateParameters(String parametersLine, Method testMethod);
}

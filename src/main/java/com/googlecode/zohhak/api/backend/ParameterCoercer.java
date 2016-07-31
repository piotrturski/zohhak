package com.googlecode.zohhak.api.backend;

import java.lang.reflect.Type;

public interface ParameterCoercer {
    Object coerceParameter(Type targetType, String stringToParse);
}

package com.googlecode.zohhak.api.backend;

import java.util.List;

public interface ConfigurationBuilder {
    List<Class<?>> getCoercers();
    String getParameterSeparator();
    String getStringBoundary();
}

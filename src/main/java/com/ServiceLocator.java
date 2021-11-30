package com;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<Class<?>, Object> DEPENDENCIES = new HashMap<>();

    public static void addDependency(Class<?> clazz, Object object) {
        DEPENDENCIES.put(clazz, object);
    }

    public static <T> T getDependency(Class<T> clazz) {
        return clazz.cast(DEPENDENCIES.get(clazz));
    }
}

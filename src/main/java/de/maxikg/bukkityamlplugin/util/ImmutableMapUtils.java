package de.maxikg.bukkityamlplugin.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ImmutableMapUtils {

    private ImmutableMapUtils() {
    }

    public static <T, U> ImmutableMap<T, U> copyOf(Map<? extends T, ? extends U> elements) {
        if (elements == null || elements.size() == 0)
            return new ImmutableMap.Builder<T, U>().build();
        else
            return ImmutableMap.copyOf(elements);
    }
}

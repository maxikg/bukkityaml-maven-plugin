package de.maxikg.bukkityamlplugin.util;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

public final class ImmutableListUtils {

    private ImmutableListUtils() {
    }

    public static <T> ImmutableList<T> copyOf(Collection<? extends T> elements) {
        if (elements == null || elements.size() == 0)
            return new ImmutableList.Builder<T>().build();
        else
            return ImmutableList.copyOf(elements);
    }
}

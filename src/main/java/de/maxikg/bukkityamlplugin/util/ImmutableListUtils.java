package de.maxikg.bukkityamlplugin.util;

import java.util.Collection;

import com.google.common.collect.ImmutableList;

public final class ImmutableListUtils {

	public static <T> ImmutableList<T> copyOf(Collection<? extends T> elements) {
		if (elements == null || elements.size() == 0)
			return new ImmutableList.Builder<T>().build();
		else
			return ImmutableList.copyOf(elements);
	}

	private ImmutableListUtils() {
	}
}

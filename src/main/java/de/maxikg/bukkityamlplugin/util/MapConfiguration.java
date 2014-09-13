package de.maxikg.bukkityamlplugin.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author maxikg <me@maxikg.de>
 */
public class MapConfiguration {

    private final Map<String, Object> data;

    public MapConfiguration() {
        this(Maps.<String, Object>newHashMap());
    }

    public MapConfiguration(Map<String, Object> data) {
        this.data = data;
    }

    public void set(String key, Object value) {
        Preconditions.checkNotNull(key);

        if (value != null)
            data.put(key, value);
    }

    public void setRequired(String key, Object value, String errorMessage) {
        Preconditions.checkNotNull(value, errorMessage);

        set(key, value);
    }

    public ImmutableMap<String, Object> getData() {
        return ImmutableMap.copyOf(data);
    }
}

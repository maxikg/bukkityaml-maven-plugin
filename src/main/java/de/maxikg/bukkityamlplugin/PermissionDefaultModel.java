package de.maxikg.bukkityamlplugin;

public enum PermissionDefaultModel {

    TRUE("true"),
    FALSE("false"),
    OP("op");

    public static final PermissionDefaultModel DEFAULT_DEFAULT = PermissionDefaultModel.TRUE;

    private final String configValue;

    PermissionDefaultModel() {
        this(null);
    }

    PermissionDefaultModel(String configValue) {
        if (configValue != null)
            this.configValue = configValue;
        else
            this.configValue = name();
    }

    public String getConfigValue() {
        return configValue;
    }
}

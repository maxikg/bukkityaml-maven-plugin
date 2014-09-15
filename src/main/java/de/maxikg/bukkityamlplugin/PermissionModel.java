package de.maxikg.bukkityamlplugin;

import com.google.common.collect.ImmutableMap;
import de.maxikg.bukkityamlplugin.util.ImmutableMapUtils;

import java.util.Map;

public class PermissionModel {

    private Map<String, Boolean> children;
    private String node, description;
    private PermissionDefaultModel def = PermissionDefaultModel.DEFAULT_DEFAULT;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PermissionDefaultModel getDefault() {
        return def;
    }

    public void setDefault(PermissionDefaultModel def) {
        this.def = def;
    }

    public void setChildren(String node, boolean value) {
        children.put(node, value);
    }

    public void unsetChildren(String node) {
        children.remove(node);
    }

    public ImmutableMap<String, Boolean> getChildren() {
        return ImmutableMapUtils.copyOf(children);
    }
}

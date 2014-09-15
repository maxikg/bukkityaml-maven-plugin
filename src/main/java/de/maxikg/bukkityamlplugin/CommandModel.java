package de.maxikg.bukkityamlplugin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.maxikg.bukkityamlplugin.util.ImmutableListUtils;

import java.util.List;

public class CommandModel {

    private List<String> aliases = Lists.newArrayList();
    private String name, description, permission, permissionMessage, usage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addAlias(String alias) {
        aliases.add(alias);
    }

    public void removeAlias(String alias) {
        aliases.remove(alias);
    }

    public ImmutableList<String> getAliases() {
        return ImmutableListUtils.copyOf(aliases);
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermissionMessage() {
        return permissionMessage;
    }

    public void setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}

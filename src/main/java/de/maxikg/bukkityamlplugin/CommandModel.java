package de.maxikg.bukkityamlplugin;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.maxikg.bukkityamlplugin.util.ImmutableListUtils;

public class CommandModel {
	
	private List<String> aliases = Lists.newArrayList();
	private String name, description, permission, permissionMessage, usage;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
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
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermissionMessage(String permissionMessage) {
		this.permissionMessage = permissionMessage;
	}
	
	public String getPermissionMessage() {
		return permissionMessage;
	}
	
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	public String getUsage() {
		return usage;
	}
}

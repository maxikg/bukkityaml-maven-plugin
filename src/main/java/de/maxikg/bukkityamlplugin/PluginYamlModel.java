package de.maxikg.bukkityamlplugin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.maxikg.bukkityamlplugin.util.ImmutableListUtils;

import java.util.List;

public class PluginYamlModel {

    private List<String> authors = Lists.newArrayList();
    private List<String> depend = Lists.newArrayList();
    private List<String> softDepend = Lists.newArrayList();
    private List<String> loadBefore = Lists.newArrayList();
    private List<CommandModel> commands = Lists.newArrayList();
    private List<PermissionModel> permissions = Lists.newArrayList();
    private String name, version, main, author;
    private String description, website, prefix;
    private PluginLoadModel load = PluginLoadModel.DEFAULT_CYCLE;
    private boolean database = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PluginLoadModel getLoad() {
        return load;
    }

    public void setLoad(PluginLoadModel load) {
        this.load = load;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void addAuthors(String author) {
        authors.add(author);
    }

    public void removeAuthors(String author) {
        authors.remove(author);
    }

    public ImmutableList<String> getAuthors() {
        return ImmutableListUtils.copyOf(authors);
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean getDatabase() {
        return database;
    }

    public void setDatabase(boolean database) {
        this.database = database;
    }

    public void addDepend(String pluginName) {
        depend.add(pluginName);
    }

    public void removeDepend(String pluginName) {
        depend.remove(pluginName);
    }

    public ImmutableList<String> getDepend() {
        return ImmutableListUtils.copyOf(depend);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void addSoftDepend(String pluginName) {
        softDepend.add(pluginName);
    }

    public void removeSoftDepend(String pluginName) {
        softDepend.remove(pluginName);
    }

    public ImmutableList<String> getSoftDepend() {
        return ImmutableListUtils.copyOf(softDepend);
    }

    public void addLoadBefore(String pluginName) {
        loadBefore.add(pluginName);
    }

    public void removeLoadBefore(String pluginName) {
        loadBefore.remove(pluginName);
    }

    public ImmutableList<String> getLoadBefore() {
        return ImmutableListUtils.copyOf(loadBefore);
    }

    public void addCommand(CommandModel command) {
        commands.add(command);
    }

    public void removeCommand(String name) {
        for (CommandModel command : Lists.newArrayList(commands)) {
            if (name.equals(command.getName()))
                commands.remove(command);
        }
    }

    public ImmutableList<CommandModel> getCommands() {
        return ImmutableListUtils.copyOf(commands);
    }

    public void addPermission(PermissionModel permission) {
        permissions.add(permission);
    }

    public void removePermission(String node) {
        for (PermissionModel permission : Lists.newArrayList(permissions)) {
            if (node.equals(permission.getNode()))
                commands.remove(permission);
        }
    }

    public ImmutableList<PermissionModel> getPermissions() {
        return ImmutableListUtils.copyOf(permissions);
    }
}

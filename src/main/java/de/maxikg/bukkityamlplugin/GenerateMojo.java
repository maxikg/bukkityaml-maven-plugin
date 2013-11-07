package de.maxikg.bukkityamlplugin;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.maven.model.Developer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.ImmutableSet;

@Mojo(name="generate", requiresProject=true, defaultPhase=LifecyclePhase.GENERATE_RESOURCES)
public class GenerateMojo extends AbstractMojo {
	
	@Component
	private MavenProject project;
	
	@Parameter(defaultValue="${project.build.outputDirectory}")
	private File outputDirectory;
	
	@Parameter(defaultValue="plugin.yml")
	private String filename;
	
	@Parameter(required=true)
	private PluginYamlModel pluginYaml;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		normalizeConfig();
		
		YamlConfiguration config = new YamlConfiguration();
		
		config.set("name", pluginYaml.getName());
		config.set("version", pluginYaml.getVersion());
		config.set("description", pluginYaml.getDescription());
		config.set("load", pluginYaml.getLoad().name());
		config.set("author", pluginYaml.getAuthor());
		
		String[] authors = pluginYaml.getAuthors().toArray(new String[0]);
		if (authors.length > 0)
			config.set("authors", authors);
		
		config.set("website", pluginYaml.getWebsite());
		config.set("main", pluginYaml.getMain());
		config.set("database", pluginYaml.getDatabase());
		
		String[] depend = pluginYaml.getDepend().toArray(new String[0]);
		if (depend.length > 0)
			config.set("depend", depend);
		
		config.set("prefix", pluginYaml.getPrefix());
		
		String[] softDepend = pluginYaml.getSoftDepend().toArray(new String[0]);
		if (softDepend.length > 0)
			config.set("softdepend", softDepend);
		
		String[] loadBefore = pluginYaml.getLoadBefore().toArray(new String[0]);
		if (loadBefore.length > 0)
			config.set("loadbefore", loadBefore);
		
		for (CommandModel cmd : pluginYaml.getCommands()) {
			if (cmd.getName() == null)
				throw new MojoFailureException("Command name not given.");
			
			config.set("commands." + cmd.getName(), serializeCommand(cmd));
		}
		
		for (PermissionModel perm : pluginYaml.getPermissions()) {
			if (perm.getNode() == null)
				throw new MojoFailureException("Permission node not given.");
			
			config.set("permissions." + perm.getNode(), serializePermission(perm));
		}
		
		File target = new File(outputDirectory, filename);
		try {
			config.save(target);
		} catch (IOException e) {
			throw new MojoFailureException("Cannot save plugin.yml", e);
		}
		getLog().info("plugin.yml generated.");
	}
	
	private ConfigurationSection serializePermission(PermissionModel model) {
		ConfigurationSection config = new MemoryConfiguration();
		
		config.set("description", model.getDescription());
		config.set("default", model.getDefault().name());
		
		ImmutableSet<Entry<String, Boolean>> children = model.getChildren().entrySet();
		if (children.size() > 0) {
			ConfigurationSection childs = new MemoryConfiguration();
			for (Entry<String, Boolean> child : children)
				config.set(child.getKey(), child.getValue());
			config.set("children", childs);
		}
		
		return config;
	}
	
	private ConfigurationSection serializeCommand(CommandModel model) {
		ConfigurationSection config = new MemoryConfiguration();
		
		config.set("description", model.getDescription());
		
		String[] aliases = model.getAliases().toArray(new String[0]);
		if (aliases.length > 0)
			config.set("aliases", aliases);
		
		config.set("permission", model.getPermission());
		config.set("permission-message", model.getPermissionMessage());
		config.set("usage", model.getUsage());
		
		return config;
	}
	
	private void normalizeConfig() throws MojoFailureException {
		if (pluginYaml.getName() == null)
			pluginYaml.setName(project.getName() != null ? project.getName() : project.getArtifactId());
		
		if (pluginYaml.getVersion() == null)
			pluginYaml.setVersion(project.getVersion());
		
		if (pluginYaml.getMain() == null)
			throw new MojoFailureException("main cannot be null.");
		
		if (pluginYaml.getWebsite() == null)
			pluginYaml.setWebsite(project.getUrl());
		
		if (pluginYaml.getAuthors().size() == 0) {
			for (Developer dev : project.getDevelopers()) {
				String name = dev.getName() != null ? dev.getName() : dev.getId();
				
				if (name == null) {
					getLog().warn("Invalid developer detected.");
					continue;
				}
				
				pluginYaml.addAuthors(name);
			}
		}
	}
}

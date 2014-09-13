package de.maxikg.bukkityamlplugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import de.maxikg.bukkityamlplugin.util.MapConfiguration;
import org.apache.maven.model.Developer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.google.common.collect.ImmutableSet;
import org.yaml.snakeyaml.Yaml;

@Mojo(name="generate", requiresProject=true, defaultPhase=LifecyclePhase.GENERATE_RESOURCES)
public class GenerateMojo extends AbstractMojo {

    private static final Yaml YAML = new Yaml();

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

        MapConfiguration config = new MapConfiguration();

        config.setRequired("name", pluginYaml.getName(), "name must be set.");
        config.setRequired("version", pluginYaml.getVersion(), "version must be set.");
		config.set("description", pluginYaml.getDescription());
		config.set("load", pluginYaml.getLoad().name());
		config.set("author", pluginYaml.getAuthor());

		ImmutableList<String> authors = pluginYaml.getAuthors();
		if (!authors.isEmpty())
			config.set("authors", authors.toArray(new String[authors.size()]));
		
		config.set("website", pluginYaml.getWebsite());
		config.setRequired("main", pluginYaml.getMain(), "main must be set.");
		config.set("database", pluginYaml.getDatabase());

        ImmutableList<String> depend = pluginYaml.getDepend();
		if (!depend.isEmpty())
			config.set("depend", depend.toArray(new String[depend.size()]));
		
		config.set("prefix", pluginYaml.getPrefix());

        ImmutableList<String> softDepend = pluginYaml.getSoftDepend();
		if (!softDepend.isEmpty())
			config.set("softdepend", softDepend.toArray(new String[softDepend.size()]));

        ImmutableList<String> loadBefore = pluginYaml.getLoadBefore();
		if (!loadBefore.isEmpty())
			config.set("loadbefore", loadBefore.toArray(new String[loadBefore.size()]));
		
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

        String configContent = YAML.dump(config.getData());
		File target = new File(outputDirectory, filename);
        PrintWriter pw;
		try {
            outputDirectory.mkdirs();

            if (!target.exists()) {
                if (!target.createNewFile())
                    throw new MojoFailureException("File was not created.");
            } else if (target.isDirectory()) {
                throw new MojoFailureException("Target is an directory.");
            }

            pw = new PrintWriter(target);
            pw.write(configContent);
            pw.flush();
            pw.close();
		} catch (IOException e) {
			throw new MojoFailureException("Cannot save plugin.yml", e);
		}
		getLog().info("plugin.yml generated.");
	}
	
	private Map<String, Object> serializePermission(PermissionModel model) {
		Map<String, Object> config = Maps.newHashMap();
		
		config.put("description", model.getDescription());
		config.put("default", model.getDefault().name());
		
		ImmutableSet<Entry<String, Boolean>> children = model.getChildren().entrySet();
		if (!children.isEmpty()) {
			config.put("children", children);
		}
		
		return config;
	}
	
	private Map<String, Object> serializeCommand(CommandModel model) {
        Map<String, Object> config = Maps.newHashMap();
		
		config.put("description", model.getDescription());
		
		ImmutableList<String> aliases = model.getAliases();
		if (!aliases.isEmpty())
			config.put("aliases", aliases.toArray(new String[aliases.size()]));
		
		config.put("permission", model.getPermission());
		config.put("permission-message", model.getPermissionMessage());
		config.put("usage", model.getUsage());
		
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

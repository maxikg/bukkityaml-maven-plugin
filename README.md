# bukkityaml-maven-plugin

This plugin generates the plugin.yml required for Bukkit plugins. By default
it will use options which are controlled by Apache Maven (e.g. developers,
name, url and so on).

## Behaviour

* The build will fail if there is no name, no command name or no permission
  node is specified in the affected sections (but it will run without specify
  permissions or commands)
* All developers in Maven are authors in plugin.yml if they are no developers
  specified in the pluginYaml-Section
* If there is no name specified in the pluginYaml-Section they will use the
  project name or if this isn't specified too they will use the artifact id
* If there is no version specified in the pluginYaml-Section they will use the
  project version
* If there is no website specified in the pluginYaml-Section they will use the
  project url and if this isn't specified too they won't set this option
* By default a file named plugin.yml is created in the compiled sources
  directory

## Example

```xml
<build>
	<plugins>
		<plugin>
			<groupId>de.maxikg</groupId>
			<artifactId>bukkityaml-maven-plugin</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<configuration>
				<pluginYaml>
					<name>TestPlugin</name>
					<main>de.maxikg.bukkittest.MainPlugin</main>
					<load>POSTWORLD</load>
					<commands>
						<command>
							<name>test</name>
							<description>A simple test command</description>
						</command>
					</commands>
					<permissions>
						<permission>
							<node>test.sayHello</node>
							<description>Allows to say Hello</description>
						</permission>
					</permissions>
				</pluginYaml>
			</configuration>
			<executions>
				<execution>
					<goals>
						<goal>generate</goal>
					</goals>
				</execution>
			</executions>
		</plugin>
		
		[...]
	</plugins>
	
	[...]
</build>
```
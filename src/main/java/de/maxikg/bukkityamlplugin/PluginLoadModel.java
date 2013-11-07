package de.maxikg.bukkityamlplugin;

public enum PluginLoadModel {
	
	STARTUP,
	POSTWORLD;
	
	public static final PluginLoadModel DEFAULT_CYCLE = PluginLoadModel.STARTUP;
	
	private final String configValue;
	
	PluginLoadModel() {
		this(null);
	}
	
	PluginLoadModel(String configValue) {
		if (configValue != null)
			this.configValue = configValue;
		else
			this.configValue = name();
	}
	
	public String getConfigValue() {
		return configValue;
	}
}

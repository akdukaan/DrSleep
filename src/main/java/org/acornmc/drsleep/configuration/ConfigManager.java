package org.acornmc.drsleep.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {

    Plugin pluginRef;
    FileConfiguration fileConfiguration;

    public ConfigManager(Plugin plugin) {
        plugin.saveDefaultConfig();
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();

        pluginRef = plugin;
        fileConfiguration = pluginRef.getConfig();
    }

    // returns the current file configuration
    // TODO: rename method
    public FileConfiguration get() {
        return fileConfiguration;
    }

    // call this function to reload the stored configuration
    public void reload() {
        pluginRef.reloadConfig();
        fileConfiguration = pluginRef.getConfig();
    }
}

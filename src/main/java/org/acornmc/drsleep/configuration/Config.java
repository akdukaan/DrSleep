package org.acornmc.drsleep.configuration;

import org.acornmc.drsleep.DrSleep;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;

public class Config {

    public static String LANGUAGE_FILE = "lang-en.yml";
    public static boolean ENABLE_BSTATS = true;
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss z";
    public static boolean CLEAR_LIST_DAILY = true;
    public static boolean DEBUG = false;
    public static Integer SMOOTH_TRANSITION_TICKS = 0;

    private static YamlConfiguration config;

    private static void init() {
        LANGUAGE_FILE = getString("language-file", LANGUAGE_FILE);
        ENABLE_BSTATS = getBoolean("enable-bstats", true);
        TIME_FORMAT = getString("time-format", TIME_FORMAT);
        CLEAR_LIST_DAILY = getBoolean("clear-list-daily", true);
        DEBUG = getBoolean("debug", false);
        SMOOTH_TRANSITION_TICKS = getInt("smooth-transition-ticks", SMOOTH_TRANSITION_TICKS);
    }

    // ############################  DO NOT EDIT BELOW THIS LINE  ############################

    public static void reload() {
        reload(DrSleep.plugin);
    }

    /**
     * Reload the configuration file
     */
    public static void reload(Plugin plugin) {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load config.yml, please correct your syntax errors", ex);
            throw new RuntimeException(ex);
        }
        config.options().setHeader(Collections.singletonList("This is the configuration file for " + plugin.getName()));
        config.options().copyDefaults(true);

        Config.init();

        try {
            config.save(configFile);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + configFile, ex);
        }
    }

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    private static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }
}

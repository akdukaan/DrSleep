package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.UUID;
import java.util.Set;
import org.bukkit.scheduler.BukkitScheduler;

public final class DrSleep extends JavaPlugin {

    public static DrSleep plugin;
    public static Set<UUID> nosleep = new HashSet<>();

    ConfigManager configManager;

    @Override
    public void onEnable() {
        plugin = this;

        configManager = new ConfigManager(this);

        getCommand("nosleep").setExecutor(new CommandNoSleep(configManager));
        getCommand("drsleep").setExecutor(new CommandDrSleep(configManager));
        getServer().getPluginManager().registerEvents(new EventPlayerBedEnter(configManager), this);
        getServer().getPluginManager().registerEvents(new EventLogout(), this);

        if (configManager.get().getBoolean("ClearNosleepDaily")) {
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(this, () -> {
                if (Bukkit.getWorld(configManager.get().getString("World")).getTime() < 20L) {
                    for (UUID uuid : nosleep) {
                        nosleep.remove(uuid);
                        Bukkit.getPlayer(uuid).sendMessage(getConfig().getString("RemovedFromNoSleep").replace("&", "§"));
                    }
                }
            }, 0L, 20L);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package org.acornmc.drsleep;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.UUID;
import java.util.Set;
import org.bukkit.scheduler.BukkitScheduler;

public final class DrSleep extends JavaPlugin {

    public static DrSleep plugin;
    public static Set<UUID> nosleep = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        plugin = this;
        getCommand("nosleep").setExecutor(new CommandNoSleep());
        getCommand("drsleep").setExecutor(new CommandDrSleep());
        getServer().getPluginManager().registerEvents(new EventPlayerBedEnter(), this);
        getServer().getPluginManager().registerEvents(new EventLogout(), this);

        if (getConfig().getBoolean("ClearNosleepDaily")) {
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(this, () -> {
                if (Bukkit.getWorld(getConfig().getString("World")).getTime() < 20L) {
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

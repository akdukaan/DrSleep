package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.Config;
import org.acornmc.drsleep.configuration.Lang;
import org.bstats.bukkit.Metrics;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import java.util.*;

import org.bukkit.scheduler.BukkitScheduler;

public final class DrSleep extends JavaPlugin {

    public static DrSleep plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Config.reload(this);
        Lang.reload(this);

        getCommand("allowsleep").setExecutor(new CommandAllowsleep());
        getCommand("preventsleep").setExecutor(new CommandPreventsleep());
        getCommand("togglesleep").setExecutor(new CommandTogglesleep());
        getCommand("drsleep").setExecutor(new CommandDrSleep());

        getServer().getPluginManager().registerEvents(new EventPlayerBedEnter(), this);
        getServer().getPluginManager().registerEvents(new EventLogout(), this);
        getServer().getPluginManager().registerEvents(new EventPlayerWorldSwitch(), this);
        getServer().getPluginManager().registerEvents(new EventWorldInit(), this);

        int pluginId = 16042;
        new Metrics(this, pluginId);

        List<World> worlds = Bukkit.getWorlds();
        for (World w : worlds) {
            Debug.log("world found " + w.getName());
            if (w.getEnvironment() == World.Environment.NORMAL) {
                Debug.log("managing world");
                ManagedWorld.managedWorlds.put(w, new ManagedWorld(w));
            }
        }

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(this, () -> {
                if (Config.CLEAR_LIST_DAILY) {
                    Collection<ManagedWorld> keySet = ManagedWorld.managedWorlds.values();
                    for (ManagedWorld m : keySet) {
                        m.performTimedTask();
                    }
                }
            }, 0L, 20);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        }
    }

    @Override
    public void onDisable() {
        for (ManagedWorld mw : ManagedWorld.managedWorlds.values()) {
            mw.clearPreventingSleep();
        }
    }
}

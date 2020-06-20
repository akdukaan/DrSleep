package org.acornmc.drsleep;

import org.bukkit.event.EventHandler;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerBedEnterEvent;
import java.util.UUID;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class EventPlayerBedEnter implements Listener {
    FileConfiguration config;
    Set<UUID> nosleep;

    public EventPlayerBedEnter() {
        this.config = DrSleep.plugin.getConfig();
        this.nosleep = DrSleep.nosleep;
    }

    @EventHandler
    public void onPlayerBedEnter(final PlayerBedEnterEvent event) {
        if (!event.getBedEnterResult().toString().equals("OK")) {
            return;
        }
        final Player player = event.getPlayer();
        final World world = Bukkit.getWorld(config.getString("World"));
        if (world == null) {
            player.sendMessage("§cDrSleep configuration error: You don't have a world called " + config.getString("World") + ".");
            return;
        }
        if (nosleep.contains(player.getUniqueId())) {
            nosleep.remove(player.getUniqueId());
            player.sendMessage(config.getString("RemovedFromNoSleep").replace("&", "§"));
        }
        if (nosleep.isEmpty()) {
            player.sendMessage(config.getString("DoesSleep").replace("&", "§"));
            world.setThundering(false);
            world.setStorm(false);
            world.setTime(0L);
            return;
        }
        player.sendMessage(config.getString("CannotSleep").replace("&", "§").replace("%count%", Integer.toString(nosleep.size())));
    }
}

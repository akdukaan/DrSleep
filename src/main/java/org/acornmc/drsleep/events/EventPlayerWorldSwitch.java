package org.acornmc.drsleep.events;

import org.acornmc.drsleep.DrSleep;
import org.acornmc.drsleep.configuration.ConfigManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Set;
import java.util.UUID;

public class EventPlayerWorldSwitch implements Listener {
    Set<UUID> nosleep;
    ConfigManager configManager;

    public EventPlayerWorldSwitch(ConfigManager configManager) {
        this.nosleep = DrSleep.nosleep;
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        final World world = player.getWorld();
        if (!nosleep.contains(player.getUniqueId())) {
            return;
        }
        if (!world.getName().equals(configManager.get().getString("World"))) {
            nosleep.remove(player.getUniqueId());
            player.sendMessage(configManager.get().getString("RemovedFromNoSleep").replace("&", "§"));
        }
    }
}

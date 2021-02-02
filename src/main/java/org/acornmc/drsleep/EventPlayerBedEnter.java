package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.ConfigManager;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerBedEnterEvent;
import java.util.UUID;
import java.util.Set;
import org.bukkit.event.Listener;

public class EventPlayerBedEnter implements Listener {
    Set<UUID> nosleep;

    ConfigManager configManager;

    public EventPlayerBedEnter(ConfigManager configManager) {
        this.nosleep = DrSleep.nosleep;
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerBedEnter(final PlayerBedEnterEvent event) {
        if (!event.getBedEnterResult().toString().equals("OK")) {
            return;
        }
        final Player player = event.getPlayer();
        final World world = Bukkit.getWorld(configManager.get().getString("World"));
        if (world == null) {
            player.sendMessage("§cDrSleep configuration error: You don't have a world called " + configManager.get().getString("World") + ".");
            return;
        }
        if (player.getWorld() != world) {
            return;
        }
        if (nosleep.contains(player.getUniqueId())) {
            nosleep.remove(player.getUniqueId());
            player.sendMessage(configManager.get().getString("RemovedFromNoSleep").replace("&", "§"));
        }
        if (nosleep.isEmpty()) {
            world.setTime(0L);
            if (world.isThundering()) {
                world.setThundering(false);
            }
            if (world.hasStorm()) {
                world.setStorm(false);
            }
            Bukkit.broadcastMessage(configManager.get().getString("DoesSleep").replace("%PLAYER%", player.getName()).replace("&", "§"));
            if(configManager.get().getBoolean("ClearInsomiaAfterSleep") == true){
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.setStatistic(Statistic.TIME_SINCE_REST, 0);
                }
            }
            return;
        }
        player.sendMessage(configManager.get().getString("CannotSleep").replace("&", "§").replace("%count%", Integer.toString(nosleep.size())));
    }
}

package org.acornmc.drsleep;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;

public class EventLogout implements Listener {

    @EventHandler
    public void onLogout(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Util.processPlayerRemoval(player);
    }
}
package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class EventPlayerWorldSwitch implements Listener {

    @EventHandler
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        boolean success = Util.processPlayerRemoval(player);
        if (!success) return;
        Lang.send(player, Lang.NOW_ALLOWING_SKIP);
    }
}

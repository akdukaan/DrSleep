package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.Listener;

public class EventPlayerBedEnter implements Listener {

    @EventHandler
    public void onPlayerBedEnter(final PlayerBedEnterEvent event) {
        if (!event.getBedEnterResult().toString().equals("OK")) return;
        Player player = event.getPlayer();
        Util.processPlayerRemoval(player);
        World w = player.getWorld();
        ManagedWorld m = Util.getManagedWorld(w);
        if (m == null) return;
        if (m.preventingSleep.size() > 0) {
            Lang.send(player, Lang.CANNOT_SKIP);
            return;
        }
        w.setTime(0L);
        if (w.isThundering()) {
            w.setThundering(false);
        }
        if (w.hasStorm()) {
            w.setStorm(false);
        }
        String msg = Lang.SKIPPED_NIGHT;
        msg = msg.replace("%PLAYER%", player.getName());
        for (Player p : w.getPlayers()) {
            Lang.send(p, msg);
        }
    }
}

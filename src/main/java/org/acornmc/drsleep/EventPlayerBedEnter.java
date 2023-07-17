package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.Config;
import org.acornmc.drsleep.configuration.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import static org.acornmc.drsleep.DrSleep.plugin;

public class EventPlayerBedEnter implements Listener {

    @EventHandler
    public void onPlayerBedEnter(final PlayerBedEnterEvent event) {
        if (!event.getBedEnterResult().toString().equals("OK")) return;
        Player player = event.getPlayer();
        Util.processPlayerRemoval(player);
        World w = player.getWorld();
        ManagedWorld m = Util.getManagedWorld(w);
        if (m == null) {
            Debug.log("managed world was null");
            Debug.log(ManagedWorld.managedWorlds.size() + " is the size of managed worlds");
            Debug.log(ManagedWorld.managedWorlds.toString());
            return;
        }
        if (!m.preventingSleep.isEmpty()) {
            Lang.send(player, Lang.CANNOT_SKIP);
            return;
        }

        if (Config.SMOOTH_TRANSITION_TICKS > 1) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!m.preventingSleep.isEmpty()) {
                        Lang.send(player, Lang.CANNOT_SKIP);
                        player.teleport(event.getBed().getLocation());
                        cancel();
                    }
                    if (!player.isSleeping()) {
                        cancel();
                    }

                    long addedTime = w.getTime() + Config.SMOOTH_TRANSITION_TICKS;
                    // 23400 is the time when the day resets to 0
                    w.setTime(addedTime > 23400 ? 0L : addedTime);
                    // 12542 is the time when night starts
                    if (w.getTime() < 12542) {
                        cancel();

                        String msg = Lang.SKIPPED_NIGHT;
                        msg = msg.replace("%PLAYER%", player.getName());
                        for (Player p : w.getPlayers()) {
                            Lang.send(p, msg);
                        }

                        if (w.isThundering()) {
                            w.setThundering(false);
                        }
                        if (w.hasStorm()) {
                            w.setStorm(false);
                        }
                    }
                }
            }.runTaskTimer(plugin, 1L, 1L);
        }
        else {
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
}

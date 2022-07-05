package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.Lang;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;

public class ManagedWorld {
    public static HashMap<World, ManagedWorld> managedWorlds;

    public HashSet<Player> preventingSleep = new HashSet<>();
    public World world;

    public ManagedWorld(World world) {
        this.world = world;
    }

    public void clearPreventingSleep() {
        for (Player player : preventingSleep) {
            Lang.send(player, Lang.NOW_ALLOWING_SKIP);
        }
        preventingSleep.clear();
    }

    public void performTimedTask() {
        if (world == null) return;
        if (world.getTime() >= 20) return;

        clearPreventingSleep();
    }
}

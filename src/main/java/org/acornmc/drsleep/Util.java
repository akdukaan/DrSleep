package org.acornmc.drsleep;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class Util {
    /**
     *
     * @param player the player to remove
     * @return true if it worked
     */
    public static boolean processPlayerRemoval(Player player) {
        World w = player.getWorld();
        ManagedWorld m = getManagedWorld(w);
        if (m == null) return false;
        if (m.preventingSleep.contains(player)) {
            m.preventingSleep.remove(player);
            return true;
        }
        return false;
    }

    /**
     *
     * @param player the player to add
     * @return true if it worked
     */
    public static boolean processPlayerAddition(Player player) {
        World w = player.getWorld();
        ManagedWorld m = getManagedWorld(w);
        if (m == null) return false;
        m.preventingSleep.add(player);
        return true;
    }

    public static boolean isPlayerPreventingSleep(Player player) {
        World w = player.getWorld();
        ManagedWorld m = getManagedWorld(w);
        if (m == null) return false;
        return m.preventingSleep.contains(player);
    }

    public static ManagedWorld getManagedWorld(World w) {
        return ManagedWorld.managedWorlds.get(w);
    }
}

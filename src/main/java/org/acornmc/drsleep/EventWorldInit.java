package org.acornmc.drsleep;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class EventWorldInit implements Listener {

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        World w = event.getWorld();
        Debug.log("world found " + w.getName());
        if (w.getEnvironment() == World.Environment.NORMAL) {
            Debug.log("managing world");
            ManagedWorld.managedWorlds.put(w, new ManagedWorld(w));
        }
    }
}

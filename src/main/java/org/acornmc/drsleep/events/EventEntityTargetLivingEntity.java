package org.acornmc.drsleep.events;

import org.acornmc.drsleep.DrSleep;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;
import java.util.UUID;

public class EventEntityTargetLivingEntity implements Listener {

    Set<UUID> nophantom;

    public EventEntityTargetLivingEntity() {
        this.nophantom = DrSleep.nophantom;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityTargetLivingEntity(final EntityTargetLivingEntityEvent event) {
        final LivingEntity entity = event.getTarget();
        if (event.getEntityType().equals(EntityType.PHANTOM)) {
            if(entity instanceof Player){
                Player player = (Player) entity;
                if (nophantom.contains(player.getUniqueId())){
                    player.setStatistic(Statistic.TIME_SINCE_REST, 0);
                    event.getEntity().remove();
                }
            }
        }
    }

}

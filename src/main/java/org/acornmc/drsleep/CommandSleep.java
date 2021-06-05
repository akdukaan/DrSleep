package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.UUID;
import java.util.Set;
import org.bukkit.command.CommandExecutor;

public class CommandSleep implements CommandExecutor {
    Set<UUID> nosleep;
    ConfigManager configManager;
    public CommandSleep(ConfigManager configManager) {
        this.nosleep = DrSleep.nosleep;
        this.configManager = configManager;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("drsleep.sleep")) {
            player.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
            return true;
        }
        World playerWorld = player.getWorld();
        if (!playerWorld.getName().equals(configManager.get().getString("World"))) {
            player.sendMessage(configManager.get().getString("NotInConfiguredWorld").replace("&", "§"));
            return true;
        }
        final World world = Bukkit.getWorld(configManager.get().getString("World"));
        if (player.getWorld() != world) {
            player.sendMessage(configManager.get().getString("NotInConfiguredWorld").replace("&", "§"));
            return true;
        }
        if (nosleep.contains(player.getUniqueId())) {
            nosleep.remove(player.getUniqueId());
            player.sendMessage(configManager.get().getString("RemovedFromNoSleep").replace("&", "§"));
        }
        if (nosleep.isEmpty()) {
            if (world.getTime() > 0 && world.getTime() < 12300 ) {
                player.sendMessage("§cYou can only sleep at night.");
                return true;
            }
            world.setTime(0L);
            if (world.isThundering()) {
                world.setThundering(false);
            }
            if (world.hasStorm()) {
                world.setStorm(false);
            }
            Bukkit.broadcastMessage(configManager.get().getString("DoesSleep").replace("%PLAYER%", player.getName()).replace("&", "§"));
            if (configManager.get().getBoolean("ClearInsomiaAfterSleep") == true) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.setStatistic(Statistic.TIME_SINCE_REST, 0);
                }
            }
            return true;
        }
    return true;
    }
}
package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.ConfigManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.UUID;
import java.util.Set;
import org.bukkit.command.CommandExecutor;

public class CommandNoSleep implements CommandExecutor {
    Set<UUID> nosleep;
    ConfigManager configManager;

    public CommandNoSleep(ConfigManager configManager) {
        this.nosleep = DrSleep.nosleep;
        this.configManager = configManager;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only");
            return true;
        }
        Player player = (Player)sender;
        if (!player.hasPermission("drsleep.nosleep")) {
            player.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
            return true;
        }
        World playerWorld = player.getWorld();
        if (!playerWorld.getName().equals(configManager.get().getString("World"))) {
            player.sendMessage(configManager.get().getString("NotInConfiguredWorld").replace("&", "§"));
            return true;
        }
        if (nosleep.contains(player.getUniqueId())) {
            nosleep.remove(player.getUniqueId());
            player.sendMessage(configManager.get().getString("RemovedFromNoSleep").replace("&", "§"));
            return true;
        }
        nosleep.add(player.getUniqueId());
        player.sendMessage(configManager.get().getString("AddedToNoSleep").replace("&", "§"));
        return true;
    }
}
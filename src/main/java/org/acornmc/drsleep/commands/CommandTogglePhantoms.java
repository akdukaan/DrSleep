package org.acornmc.drsleep.commands;

import org.acornmc.drsleep.DrSleep;
import org.acornmc.drsleep.configuration.ConfigManager;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class CommandTogglePhantoms implements CommandExecutor {

    Set<UUID> nophantom;
    ConfigManager configManager;

    public CommandTogglePhantoms(ConfigManager configManager) {
        this.nophantom = DrSleep.nophantom;
        this.configManager = configManager;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only");
            return true;
        }
        Player player = (Player)sender;
        if (!player.hasPermission("drsleep.togglephantoms")) {
            player.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
            return true;
        }
        World playerWorld = player.getWorld();
        if (!playerWorld.getName().equals(configManager.get().getString("World"))) {
            player.sendMessage(configManager.get().getString("NotInConfiguredWorld").replace("&", "§"));
            return true;
        }
        if (nophantom.contains(player.getUniqueId())) {
            nophantom.remove(player.getUniqueId());
            player.sendMessage(configManager.get().getString("RemovedFromNoPhantom").replace("&", "§"));
            return true;
        }
        nophantom.add(player.getUniqueId());
        player.sendMessage(configManager.get().getString("AddedToNoPhantom").replace("&", "§"));
        return true;
    }

}

package org.acornmc.drsleep;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.UUID;
import java.util.Set;
import org.bukkit.command.CommandExecutor;

public class CommandNoSleep implements CommandExecutor {
    Set<UUID> nosleep;
    FileConfiguration config;

    public CommandNoSleep() {
        this.nosleep = DrSleep.nosleep;
        this.config = DrSleep.plugin.getConfig();
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (player.hasPermission("drsleep.nosleep")) {
                if (nosleep.contains(player.getUniqueId())) {
                    nosleep.remove(player.getUniqueId());
                    player.sendMessage(config.getString("RemovedFromNoSleep").replace("&", "§"));
                }
                else {
                    nosleep.add(player.getUniqueId());
                    player.sendMessage(config.getString("AddedToNoSleep").replace("&", "§"));
                }
                return true;
            }
            player.sendMessage(config.getString("NoPerms").replace("&", "§"));
        }
        System.out.println("This command is for players only");
        return true;
    }
}
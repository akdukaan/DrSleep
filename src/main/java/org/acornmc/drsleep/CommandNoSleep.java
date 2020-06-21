package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.ConfigManager;
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
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (player.hasPermission("drsleep.nosleep")) {
                if (nosleep.contains(player.getUniqueId())) {
                    nosleep.remove(player.getUniqueId());
                    player.sendMessage(configManager.get().getString("RemovedFromNoSleep").replace("&", "§"));
                }
                else {
                    nosleep.add(player.getUniqueId());
                    player.sendMessage(configManager.get().getString("AddedToNoSleep").replace("&", "§"));
                }
                return true;
            }
            player.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
        }
        System.out.println("This command is for players only");
        return true;
    }
}
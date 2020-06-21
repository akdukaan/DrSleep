package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.UUID;
import java.util.Set;
import org.bukkit.command.CommandExecutor;

public class CommandDrSleep implements CommandExecutor {
    Set<UUID> nosleep;

    ConfigManager configManager;

    public CommandDrSleep(ConfigManager configManager) {
        this.nosleep = DrSleep.nosleep;
        this.configManager = configManager;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equals("reload")) {
            if (sender.hasPermission("drsleep.reload")) {
                configManager.reload();
                sender.sendMessage("§6DrSleep config reloaded.");
                return true;
            }
            sender.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
            return true;
        }
        if (args[0].equals("list")) {
            if (sender.hasPermission("drsleep.list")) {
                int count = nosleep.size();
                String list = configManager.get().getString("NoSleepList").replace("&", "§").replace("%count%", Integer.toString(count));
                for (UUID uuid : this.nosleep) {
                    String playername = Bukkit.getPlayer(uuid).getName();
                    list += " " + playername;
                }
                sender.sendMessage(list);
                return true;
            }
            sender.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
            return true;
        }
        if (args[0].equals("clear")) {
            if (sender.hasPermission("drsleep.clear")) {
                int count = nosleep.size();
                String names = configManager.get().getString("NoSleepClear").replace("&", "§").replace("%count%", Integer.toString(count));
                for (final UUID uuid : nosleep) {
                    final Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        nosleep.remove(player.getUniqueId());
                        names += " " + player.getName();
                    }
                }
                sender.sendMessage(names);
                return true;
            }
            sender.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
            return true;
        }
        return false;
    }
}

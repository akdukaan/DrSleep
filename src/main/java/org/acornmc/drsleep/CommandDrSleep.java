package org.acornmc.drsleep;

import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.UUID;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class CommandDrSleep implements CommandExecutor
{
    FileConfiguration config;
    Set<UUID> nosleep;

    public CommandDrSleep() {
        this.config = DrSleep.plugin.getConfig();
        this.nosleep = DrSleep.nosleep;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equals("reload")) {
            if (sender.hasPermission("drsleep.reload")) {
                DrSleep.plugin.reloadConfig();
                sender.sendMessage("§6DrSleep has attempted to reload the plugin. This feature may or may not work.");
                return true;
            }
            sender.sendMessage(this.config.getString("NoPerms").replace("&", "§"));
            return true;
        }
        else if (args[0].equals("list")) {
            if (sender.hasPermission("drsleep.list")) {
                sender.sendMessage(this.config.getString("NoSleepList").replace("&", "§").replace("%count%", Integer.toString(this.nosleep.size())));
                String names = "§c";
                for (final UUID uuid : this.nosleep) {
                    final Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        names = names + player.getName() + " ";
                    }
                }
                sender.sendMessage(names);
                return true;
            }
            sender.sendMessage(this.config.getString("NoPerms").replace("&", "§"));
            return true;
        }
        else {
            if (!args[0].equals("clear")) {
                return false;
            }
            if (sender.hasPermission("drsleep.clear")) {
                String names = "§6The following players have been removed from nosleep:§c";
                for (final UUID uuid : this.nosleep) {
                    final Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        names = names + " " + player.getName();
                        this.nosleep.remove(player.getUniqueId());
                    }
                }
                sender.sendMessage(names);
                return true;
            }
            sender.sendMessage(this.config.getString("NoPerms").replace("&", "§"));
            return true;
        }
    }
}

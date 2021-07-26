package org.acornmc.drsleep.commands;

import org.acornmc.drsleep.DrSleep;
import org.acornmc.drsleep.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Iterator;
import java.util.UUID;
import java.util.Set;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

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
            if (!sender.hasPermission("drsleep.reload")) {
                sender.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
                return true;
            }
            configManager.reload();
            sender.sendMessage("§6DrSleep config reloaded.");
            return true;
        }
        if (args[0].equals("list")) {
            if (!sender.hasPermission("drsleep.list")) {
                sender.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
                return true;
            }
            int count = nosleep.size();
            String list = configManager.get().getString("NoSleepList").replace("&", "§").replace("%count%", Integer.toString(count));
            for (UUID uuid : this.nosleep) {
                String playername = Bukkit.getPlayer(uuid).getName();
                list += " " + playername;
            }
            sender.sendMessage(list);
            return true;
        }
        if (args[0].equals("clear")) {
            if (!sender.hasPermission("drsleep.clear")) {
                sender.sendMessage(configManager.get().getString("NoPerms").replace("&", "§"));
                return true;
            }
            int count = nosleep.size();
            String names = configManager.get().getString("NoSleepClear").replace("&", "§").replace("%count%", Integer.toString(count));
            Iterator<UUID> iterator = nosleep.iterator();
            while (iterator.hasNext()) {
                Player player = Bukkit.getPlayer(iterator.next());
                iterator.remove();
                String playername = "{null}";
                if (player != null) {
                    playername = player.getName();
                }
                player.sendMessage(configManager.get().getString("RemovedFromNoSleep").replace("&", "§"));
                names += " " + playername;
            }
            sender.sendMessage(names);
            return true;
        }
        return false;
    }
}

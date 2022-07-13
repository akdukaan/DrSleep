package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.Config;
import org.acornmc.drsleep.configuration.Lang;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class CommandDrSleep implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equals("reload")) {
            if (!sender.hasPermission("drsleep.reload")) {
                Lang.send(sender, Lang.NO_PERMISSION);
                return true;
            }
            Config.reload();
            Lang.reload();
            Lang.send(sender, Lang.RELOADED);
            return true;
        }
        if (args[0].equals("list")) {
            if (!sender.hasPermission("drsleep.list")) {
                Lang.send(sender, Lang.NO_PERMISSION);
                return true;
            }
            Lang.send(sender, Lang.PREVENTING_SKIP_LIST_HEADER);
            for (World w : ManagedWorld.managedWorlds.keySet()) {
                String msg = Lang.PREVENTING_SKIP_LIST_ITEM;
                StringBuilder players = new StringBuilder();
                for (Player p : Util.getManagedWorld(w).preventingSleep) {
                    players.append(p.getName()).append(" ");
                }
                msg = msg.replace("%WORLD%", w.getName());
                msg = msg.replace("%PLAYERS%", players);
                Lang.send(sender, msg);
            }
            return true;
        }
        return false;
    }
}

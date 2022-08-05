package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.Config;
import org.acornmc.drsleep.configuration.Lang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandDrSleep implements TabExecutor {

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
        if (args[0].equals("clear")) {
            if (!sender.hasPermission("drsleep.clear")) {
                Lang.send(sender, Lang.NO_PERMISSION);
                return true;
            }

            // If a world was not specified, clear all worlds
            if (args.length < 2) {
                for (ManagedWorld mw : ManagedWorld.managedWorlds.values()) {
                    mw.clearPreventingSleep();
                }
                return true;
            }

            // If a world was specified, clear just that world
            String worldName = args[1];
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                return false;
            }
            ManagedWorld mw = ManagedWorld.managedWorlds.get(world);
            if (mw == null) {
                Lang.send(sender, Lang.DISALLOWED_WORLD);
                return true;
            }
            mw.clearPreventingSleep();
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("drsleep.reload")) {
                list.add("reload");
            }
            if (sender.hasPermission("drmap.clear")) {
                list.add("clear");
            }
            if (sender.hasPermission("drmap.list")) {
                list.add("list");
            }
            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
        }
        if (args.length == 2) {
            if (sender.hasPermission("drmap.clear")) {
                for (World w : ManagedWorld.managedWorlds.keySet()) {
                    list.add(w.getName());
                }
            }
            return StringUtil.copyPartialMatches(args[1], list, new ArrayList<>());
        }
        return list;
    }
}

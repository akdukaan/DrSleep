package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandAllowsleep implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            Lang.send(commandSender, Lang.NOT_PLAYER);
            return true;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("drsleep.preventsleep")) {
            Lang.send(player, Lang.NO_PERMISSION);
            return true;
        }
        boolean alreadyPreventingSleep = Util.isPlayerPreventingSleep(player);
        if (!alreadyPreventingSleep) {
            Lang.send(player, Lang.ALREADY_ALLOWING_SLEEP);
            return true;
        }
        boolean success = Util.processPlayerRemoval(player);
        if (!success) {
            Lang.send(player, Lang.DISALLOWED_WORLD);
            return true;
        }
        Lang.send(player, Lang.NOW_ALLOWING_SKIP);
        return true;
    }
}

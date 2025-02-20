package org.acornmc.drsleep;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Placeholders extends PlaceholderExpansion {

    private final Plugin plugin;

    public Placeholders(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "drsleep";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // Keep registered even if PlaceholderAPI reloads
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String identifier) {
        identifier = identifier.toLowerCase();
        if (identifier.startsWith("skippable_")) {
            String worldName = identifier.substring(identifier.indexOf('_') + 1);
            World world = Bukkit.getWorld(worldName);
            ManagedWorld managedWorld = ManagedWorld.managedWorlds.get(world);
            if (managedWorld == null) return "unknown world";
            if (managedWorld.isNightSkippable()) return "skippable";
            return "not skippable";
        }
        return null;
    }
}
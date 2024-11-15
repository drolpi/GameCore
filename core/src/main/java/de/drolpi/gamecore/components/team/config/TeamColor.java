package de.drolpi.gamecore.components.team.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;

public enum TeamColor {

    WHITE(ChatColor.WHITE, DyeColor.WHITE),
    RED(ChatColor.RED, DyeColor.RED),
    BLUE(ChatColor.BLUE, DyeColor.BLUE),
    YELLOW(ChatColor.YELLOW, DyeColor.YELLOW),
    GREEN(ChatColor.DARK_GREEN, DyeColor.GREEN),
    PINK(ChatColor.LIGHT_PURPLE, DyeColor.PINK),
    LIGHT_BLUE(ChatColor.AQUA, DyeColor.LIGHT_BLUE),
    LIGHT_GREEN(ChatColor.GREEN, DyeColor.LIME),
    ORANGE(ChatColor.GOLD, DyeColor.ORANGE);

    private final transient ChatColor chatColor;
    private final transient DyeColor dyeColor;

    TeamColor(ChatColor chatColor, DyeColor dyeColor) {
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
    }

    public ChatColor chatColor() {
        return this.chatColor;
    }

    public DyeColor dyeColor() {
        return this.dyeColor;
    }
}

package net.bote.gamecore.api;

import net.bote.gamecore.api.game.GameProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractGamePlugin extends JavaPlugin implements GamePlugin {

    @Override
    public final void onLoad() {

    }

    @Override
    public final void onEnable() {

    }

    @Override
    public final void onDisable() {

    }

    @Override
    public @NotNull GameProvider gameProvider() {
        return null;
    }
}

package net.bote.gamecore.api;

import net.bote.gamecore.api.game.GameProvider;
import org.jetbrains.annotations.NotNull;

public interface GamePlugin {

    @NotNull GameProvider gameProvider();

}

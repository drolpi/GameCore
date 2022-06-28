package net.bote.gamecore;

import net.bote.gamecore.api.game.GameController;
import org.jetbrains.annotations.NotNull;

public interface GamePlugin {

    @NotNull GameController gameController();

}

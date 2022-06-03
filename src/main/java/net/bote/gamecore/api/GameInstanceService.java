package net.bote.gamecore.api;

import net.bote.gamecore.api.game.GameInstance;
import org.jetbrains.annotations.NotNull;

public interface GameInstanceService {

    void enable(@NotNull GameInstance gameInstance);

    void disable(@NotNull GameInstance gameInstance);

}

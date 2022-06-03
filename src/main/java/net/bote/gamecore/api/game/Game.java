package net.bote.gamecore.api.game;

import net.bote.gamecore.api.phase.Phase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Game {

    @NotNull String type();

    @NotNull List<Phase> phases();

}

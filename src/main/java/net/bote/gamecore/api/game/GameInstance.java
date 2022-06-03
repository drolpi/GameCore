package net.bote.gamecore.api.game;

import net.bote.gamecore.api.eventbus.EventBus;
import net.bote.gamecore.api.phase.Phase;
import org.jetbrains.annotations.NotNull;

public interface GameInstance {

    Phase activePhase();

    void setActivePhase(@NotNull Phase phase);

    @NotNull
    EventBus eventBus();

}

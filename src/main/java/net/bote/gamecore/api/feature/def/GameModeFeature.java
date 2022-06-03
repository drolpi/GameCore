package net.bote.gamecore.api.feature.def;

import net.bote.gamecore.api.eventbus.EventBus;
import net.bote.gamecore.api.eventbus.EventListener;
import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.feature.FeatureInfo;
import net.bote.gamecore.api.game.GameInstance;
import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

@FeatureInfo(name = "GameModeFeature", description = "Changes the GameMode of all players in the phase", version = "1.0", authors = "dasdrolpi")
public class GameModeFeature extends AbstractFeature {

    private final EventListener<PlayerJoinEvent> joinListener;

    @Expose
    private GameMode gameMode = GameMode.SURVIVAL;

    public GameModeFeature() {
        this.joinListener = EventListener.of(PlayerJoinEvent.class, event -> event.getPlayer().setGameMode(this.gameMode));
    }

    @Override
    public void enable(@NotNull GameInstance gameInstance) {
        //TODO: Set GameMode for all already joined players

        EventBus eventBus = gameInstance.eventBus();

        //TODO: Maybe use custom event
        eventBus.register(this.joinListener);
    }

    @Override
    public void disable(@NotNull GameInstance gameInstance) {
        EventBus eventBus = gameInstance.eventBus();

        eventBus.register(this.joinListener);
    }

    public void setGameMode(@NotNull GameMode gameMode) {
        this.gameMode = gameMode;
    }
}

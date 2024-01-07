package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.FeatureInfo;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.jetbrains.annotations.NotNull;

@FeatureInfo(name = "GameModeFeature", description = "Changes the GameMode of all players in the phase", version = "1.0", authors = "dasdrolpi")
public class GameModeFeature extends AbstractFeature {

    private final Game game;

    @Expose
    private GameMode gameMode = GameMode.SURVIVAL;

    @Inject
    public GameModeFeature(Game game) {
        this.game = game;
    }

    @Override
    public void enable() {
        for (GamePlayer gamePlayer : this.game.allPlayers()) {
            gamePlayer.player().setGameMode(this.gameMode);
        }
    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        event.gamePlayer().player().setGameMode(this.gameMode);
    }

    public void setGameMode(@NotNull GameMode gameMode) {
        this.gameMode = gameMode;
    }
}

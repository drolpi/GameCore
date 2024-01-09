package de.drolpi.gamecore.api.game;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.drolpi.gamecore.api.Creatable;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.components.localization.MessageFormatTranslationProvider;
import de.drolpi.gamecore.components.localization.adventure.MiniMessageComponentRenderer;
import de.drolpi.gamecore.components.localization.adventure.MiniMessageTranslator;
import de.drolpi.gamecore.components.world.WorldHandler;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.event.GamePostLeaveEvent;
import de.drolpi.gamecore.api.phase.AbstractPhase;
import de.drolpi.gamecore.api.phase.Phase;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractGame implements Game, Creatable {

    @Expose
    private final List<AbstractPhase> phases = new ArrayList<>();

    private final List<GamePlayer> players = new ArrayList<>();
    private final List<GamePlayer> spectators = new ArrayList<>();
    private final List<GamePlayer> allPlayers = new ArrayList<>();
    private final Map<Class<GameData>, GameData> gameData = new HashMap<>();

    @Inject
    private Injector injector;
    @Inject
    private PluginManager pluginManager;
    @Inject
    private GameControllerImpl gameController;
    @Inject
    private WorldHandler worldHandler;
    @Inject
    private MiniMessageComponentRenderer renderer;

    private AbstractPhase activePhase;
    private boolean ended = false;

    public void start() {
        if (this.phases.isEmpty()) {
            throw new RuntimeException();
        }

        this.enablePhase(this.phases.get(0));
    }

    @Override
    public boolean join(GamePlayer gamePlayer) {
        //TODO: Check max players of team feature
        if (!this.activePhase.allowJoin()) {
            return this.spectate(gamePlayer);
        }

        if (!this.isPlaying(gamePlayer.uniqueId())) {
            this.players.add(gamePlayer);
            return this.add(gamePlayer);
        }
        return false;
    }

    @Override
    public boolean isPlaying(UUID uniqueId) {
        return this.players.stream().anyMatch(gamePlayer -> gamePlayer.uniqueId().equals(uniqueId));
    }

    @Override
    public List<GamePlayer> players() {
        return this.players;
    }

    @Override
    public boolean spectate(GamePlayer gamePlayer) {
        if (!this.activePhase.allowSpectate()) {
            return false;
        }

        if (this.isSpectating(gamePlayer.uniqueId())) {
            return false;
        }

        if (this.isPlaying(gamePlayer.uniqueId())) {
            this.players.remove(gamePlayer);
            this.allPlayers.remove(gamePlayer);
        }

        this.spectators.add(gamePlayer);
        return this.add(gamePlayer);
    }

    @Override
    public boolean isSpectating(UUID uniqueID) {
        return this.spectators.stream().anyMatch(gamePlayer -> gamePlayer.uniqueId().equals(uniqueID));
    }

    @Override
    public List<GamePlayer> spectators() {
        return this.spectators;
    }

    private boolean add(GamePlayer gamePlayer) {
        this.allPlayers.add(gamePlayer);

        final GameJoinEvent event = new GameJoinEvent(this, gamePlayer);
        this.pluginManager.callEvent(event);
        if (event.isCancelled()) {
            this.players.remove(gamePlayer);
            this.allPlayers.remove(gamePlayer);
            return false;
        }
        return true;
    }

    @Override
    public boolean leave(GamePlayer gamePlayer) {
        this.players.remove(gamePlayer);
        this.spectators.remove(gamePlayer);
        this.allPlayers.remove(gamePlayer);

        this.pluginManager.callEvent(new GamePostLeaveEvent(this, gamePlayer));
        return true;
    }

    @Override
    public boolean isParticipating(UUID uniqueId) {
        return this.allPlayers.stream().anyMatch(gamePlayer -> gamePlayer.uniqueId().equals(uniqueId));
    }

    @Override
    public List<GamePlayer> allPlayers() {
        return this.allPlayers;
    }

    @Override
    public void nextPhase() {
        if (this.activePhase == null) {
            //FINAL END
            return;
        }

        int activePhaseIndex = this.phases.indexOf(this.activePhase);
        int nextPhaseIndex = activePhaseIndex + 1;

        if (this.phases.size() == nextPhaseIndex) {
            this.enablePhase(null);
            return;
        }

        this.enablePhase(this.phases.get(nextPhaseIndex));
    }

    private void enablePhase(AbstractPhase nextPhase) {
        if (this.activePhase != null) {
            this.activePhase.disable();
            this.activePhase = null;
        }

        if (nextPhase == null) {
            this.stopGame();
            return;
        }

        this.activePhase = nextPhase;
        this.activePhase.enable();
    }

    public <T extends AbstractPhase> T createPhase(Class<T> type) {
        return this.gameController.loadPhase(this.injector, type, true);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractPhase> T phase(Class<T> type) {
        for (AbstractPhase phase : this.phases) {
            if (!phase.getClass().equals(type)) {
                continue;
            }

            return (T) phase;
        }

        throw new RuntimeException();
    }

    @Override
    public AbstractPhase activePhase() {
        return this.activePhase;
    }

    @Override
    public @NotNull List<Phase> phases() {
        return new ArrayList<>(this.phases);
    }

    public List<AbstractPhase> abstractPhases() {
        return this.phases;
    }

    @Override
    public boolean isEnded() {
        return this.ended;
    }

    @Override
    public void endGame() {
        AbstractPhase phase = this.phases.get(this.phases.size() - 1);
        this.enablePhase(phase.allowJoin() ? phase : null);
    }

    @Override
    public void stopGame() {
        if (this.ended) {
            return;
        }

        this.ended = true;
        this.gameController.removeGame(this);
        this.nextPhase();

        while (this.players.size() != 0) {
            this.leave(this.players.get(0));
        }
        while (this.spectators.size() != 0) {
            this.leave(this.spectators.get(0));
        }
        Bukkit.broadcast(Component.text("END"));
    }

    @Override
    public <T extends GameData> Optional<T> gameData(Class<T> key) {
        //noinspection unchecked
        return Optional.ofNullable((T) gameData.get(key));
    }

    @Override
    public void storeGameData(GameData data) {
        //noinspection unchecked
        gameData.put((Class<GameData>) data.getClass(), data);
    }

    public Injector injector() {
        return this.injector;
    }

    public MiniMessageComponentRenderer renderer() {
        return this.renderer;
    }
}

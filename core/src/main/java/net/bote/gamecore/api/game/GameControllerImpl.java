package net.bote.gamecore.api.game;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.bote.gamecore.GamePlugin;
import net.bote.gamecore.api.IdentifiableListDeserializer;
import net.bote.gamecore.api.IdentifiableModule;
import net.bote.gamecore.api.condition.AbstractVictoryCondition;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.feature.def.WinDetectionFeature;
import net.bote.gamecore.api.phase.AbstractPhase;
import net.bote.gamecore.api.phase.Phase;
import net.bote.gamecore.api.player.GamePlayer;
import net.bote.gamecore.internal.config.ConfigLoader;
import net.bote.gamecore.internal.config.GlobalConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Singleton
public final class GameControllerImpl implements GameController {

    private final static Type PHASE_LIST_TYPE = new TypeToken<List<AbstractPhase>>() {

    }.getType();

    private final static Type FEATURE_LIST_TYPE = new TypeToken<List<AbstractFeature>>() {

    }.getType();

    private final static Type CONDITION_LIST_TYPE = new TypeToken<List<AbstractVictoryCondition>>() {

    }.getType();

    private final GamePlugin plugin;
    private final Injector injector;
    private final Gson gson;
    private final GlobalConfig config;

    private final Set<GameType> gameTypes = new HashSet<>();
    private final Set<AbstractGame> games = new HashSet<>();
    private AbstractGame defaultGame;

    @Inject
    public GameControllerImpl(GamePlugin plugin, Injector injector, Gson gson, ConfigLoader configLoader) {
        this.plugin = plugin;
        this.injector = injector;
        this.gson = gson;
        this.config = configLoader.globalConfig();
    }

    @Override
    public GameType createGameType(@NotNull Class<? extends Game> type) {
        final GameInfo gameInfo = type.getAnnotation(GameInfo.class);

        final GameType gameType = new GameTypeImpl(gameInfo.name(), type);
        this.gameTypes.add(gameType);
        return gameType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Game> @NotNull T startGame(@NotNull GameType gameType) {
        final Class<? extends Game> type = gameType.type();
        System.out.println("Loading Game: " + type);

        final File file = new File(this.plugin.getDataFolder(), type.getSimpleName() + ".json");
        final Class<? extends AbstractGame> abstractType = (Class<? extends AbstractGame>) type;

        if (!file.exists()) {
            System.out.println("Creating Game: " + type);
            final Injector gameInjector = this.createGameInjector(abstractType);
            final AbstractGame game = gameInjector.getInstance(abstractType);
            game.create();
            this.games.add(game);
            System.out.println("Created Game: " + game);
            game.start();
            System.out.println("Started Game: " + game);

            try (final FileWriter writer = new FileWriter(file)) {
                this.gson.toJson(game, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Saved Game: " + game);

            return (T) game;
        }

        System.out.println("Loading Game from file: " + type);
        try (final JsonReader reader = new JsonReader(new FileReader(file))) {
            final Injector gameInjector = this.createGameInjector(abstractType);
            final AbstractGame abstractGame = gameInjector.getInstance(abstractType);
            final Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(
                    PHASE_LIST_TYPE,
                    new IdentifiableListDeserializer<AbstractGame, AbstractPhase>(gameInjector, abstractGame, FEATURE_LIST_TYPE) {
                        @Override
                        protected AbstractPhase createChild(Injector parentInjector, AbstractGame parent, Class<? extends AbstractPhase> childType) {
                            return loadPhase(parentInjector, childType, false);
                        }

                        @Override
                        protected List<AbstractPhase> getList(AbstractGame parent) {
                            return parent.abstractPhases();
                        }

                        @Override
                        protected JsonDeserializer<?> next(AbstractPhase parent) {
                            return new IdentifiableListDeserializer<AbstractPhase, AbstractFeature>(gameInjector, parent, CONDITION_LIST_TYPE) {
                                @Override
                                protected AbstractFeature createChild(Injector parentInjector, AbstractPhase parent, Class<? extends AbstractFeature> childType) {
                                    return parent.createFeature(childType);
                                }

                                @Override
                                protected List<AbstractFeature> getList(AbstractPhase parent) {
                                    return parent.abstractFeatures();
                                }

                                @Override
                                protected JsonDeserializer<?> next(AbstractFeature parent) {
                                    return new IdentifiableListDeserializer<AbstractFeature, AbstractVictoryCondition>(gameInjector, parent, null) {
                                        @Override
                                        protected AbstractVictoryCondition createChild(Injector parentInjector, AbstractFeature parent, Class<? extends AbstractVictoryCondition> childType) {
                                            if (!(parent instanceof WinDetectionFeature winDetectionFeature)) {
                                                return null;
                                            }
                                            return winDetectionFeature.createVictoryCondition(childType);
                                        }

                                        @Override
                                        protected List<AbstractVictoryCondition> getList(AbstractFeature parent) {
                                            if (!(parent instanceof WinDetectionFeature winDetectionFeature)) {
                                                return null;
                                            }
                                            return winDetectionFeature.abstractVictoryConditions();
                                        }
                                    };
                                }
                            };
                        }
                    }
                )
                .registerTypeAdapter(abstractType, (InstanceCreator<AbstractGame>) t -> abstractGame)
                .create();

            final AbstractGame game = gson.fromJson(reader, abstractType);
            this.games.add(game);
            System.out.println("Loaded Game from file: " + game);

            game.start();
            System.out.println("Started Game: " + game);

            return (T) game;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Game> games(GamePlayer gamePlayer, boolean spectate) {
        return new HashSet<>(this.abstractGames(gamePlayer, spectate));
    }

    public Set<AbstractGame> abstractGames(GamePlayer gamePlayer, boolean spectate) {
        Set<AbstractGame> result = new HashSet<>();
        for (AbstractGame game : this.games) {
            if (game.isPlaying(gamePlayer.uniqueId())) {
                result.add(game);
                continue;
            }

            if (spectate && game.isSpectating(gamePlayer.uniqueId())) {
                result.add(game);
            }
        }
        return result;
    }

    @Override
    public Set<Game> games() {
        return new HashSet<>(this.games);
    }

    public Set<AbstractGame> abstractGames() {
        return this.games;
    }

    public void startDefaultGame() {
        if (this.config.defaultGame() != null && !this.config.defaultGame().equals("none")) {
            Optional<GameType> type = this.gameTypes.stream().filter(gameMode -> gameMode.name().equalsIgnoreCase(this.config.defaultGame())).findAny();
            if (type.isPresent()) {
                GameType gameType = type.get();
                System.out.println(gameType.name());
                this.defaultGame = this.startGame(gameType);
            }
        }
    }

    public void removeGame(AbstractGame game) {
        this.games.remove(game);
        if (game == this.defaultGame) {
            for (Player player : this.plugin.getServer().getOnlinePlayers()) {
                player.kick();
            }

            this.plugin.getServer().shutdown();
        }
    }

    public Optional<AbstractGame> defaultGame() {
        return Optional.ofNullable(this.defaultGame);
    }

    private Injector createGameInjector(Class<? extends AbstractGame> type) {
        return this.injector.createChildInjector(new IdentifiableModule<>(Game.class, AbstractGame.class, type));
    }

    public <T extends AbstractPhase> T loadPhase(Injector injector, Class<? extends T> type, boolean create) {
        System.out.println("Loading Phase: " + type);
        injector.getParent();
        final AbstractGame game = injector.getInstance(AbstractGame.class);
        System.out.println("AAAAAAA " + (injector == game.injector()));
        final Injector child = injector.createChildInjector(new IdentifiableModule<>(Phase.class, AbstractPhase.class, type));

        final T phase = child.getInstance(type);
        game.abstractPhases().add(phase);
        System.out.println("Loaded Phase: " + phase);
        System.out.println("BBBBBBB " + (child == phase.injector()));
        child.getAllBindings().forEach((key, binding) -> System.out.println(binding));
        System.out.println("--------");
        child.getParent().getAllBindings().forEach((key, binding) -> System.out.println(binding));
        System.out.println("--------");
        child.getParent().getParent().getAllBindings().forEach((key, binding) -> System.out.println(binding));

        if (!create) {
            return phase;
        }

        phase.create();
        System.out.println("Created default values for Phase: " + phase);
        return phase;
    }

    public <T extends AbstractFeature> T loadFeature(Injector injector, Class<? extends T> type) {
        injector.getParent().getParent();
        System.out.println("Loading Feature: " + type);
        final AbstractGame game = injector.getInstance(AbstractGame.class);
        final AbstractPhase phase = injector.getInstance(AbstractPhase.class);
        System.out.println("CCCC " + (phase.injector().getParent() == game.injector()));
        System.out.println("CCCCCCCCCCCCCC " + (phase.injector() == injector));

        final T feature = injector.getInstance(type);
        System.out.println(injector);
        phase.abstractFeatures().add(feature);
        System.out.println("Loaded Feature: " + feature);

        if (feature instanceof WinDetectionFeature winDetectionFeature) {
            System.out.println(winDetectionFeature.injector() == injector);
        }

        return feature;
    }
}

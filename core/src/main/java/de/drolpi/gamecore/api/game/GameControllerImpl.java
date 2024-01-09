package de.drolpi.gamecore.api.game;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.stream.JsonReader;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import de.drolpi.gamecore.AbstractGamePlugin;
import de.drolpi.gamecore.GameCorePlugin;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.IdentifiableListDeserializer;
import de.drolpi.gamecore.api.IdentifiableModule;
import de.drolpi.gamecore.api.condition.AbstractVictoryCondition;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.feature.def.WinDetectionFeature;
import de.drolpi.gamecore.api.phase.AbstractPhase;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.components.localization.MessageFormatTranslationProvider;
import de.drolpi.gamecore.components.localization.adventure.MiniMessageComponentRenderer;
import de.drolpi.gamecore.components.localization.bundle.FileJsonResourceLoader;
import de.drolpi.gamecore.internal.config.ConfigLoader;
import de.drolpi.gamecore.internal.config.GlobalConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

@Singleton
public final class GameControllerImpl implements GameController {

    private final static Type PHASE_LIST_TYPE = new TypeToken<List<AbstractPhase>>() {

    }.getType();

    private final static Type FEATURE_LIST_TYPE = new TypeToken<List<AbstractFeature>>() {

    }.getType();

    private final static Type CONDITION_LIST_TYPE = new TypeToken<List<AbstractVictoryCondition>>() {

    }.getType();

    private final GameCorePlugin plugin;
    private final Gson gson;
    private final GlobalConfig config;
    private final File dataFolder;

    private final Map<AbstractGamePlugin, GameType> gameTypes = new HashMap<>();
    private final Set<AbstractGame> games = new HashSet<>();
    private AbstractGame defaultGame;

    @Inject
    public GameControllerImpl(GameCorePlugin plugin, Gson gson, ConfigLoader configLoader, @Named("DataFolder") File dataFolder) {
        this.plugin = plugin;
        this.gson = gson;
        this.config = configLoader.globalConfig();
        this.dataFolder = dataFolder;
    }

    @Override
    public GameType createGameType(@NotNull Class<? extends Game> type, @NotNull GamePlugin plugin) {
        if (!(plugin instanceof AbstractGamePlugin gamePlugin)) {
            throw new RuntimeException();
        }

        final GameInfo gameInfo = type.getAnnotation(GameInfo.class);
        final GameType gameType = new GameTypeImpl(gameInfo.name(), type, this.dataFolder);

        final File gameDataFolder = gameType.dataFolder();

        if (gameDataFolder.mkdirs()) {
            System.out.println("Created folder for GameType: " + gameType.name());
        }

        this.gameTypes.put(gamePlugin, gameType);
        return gameType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Game> @NotNull T startGame(@NotNull GameType gameType) {
        final Class<? extends Game> type = gameType.type();
        System.out.println("Loading Game: " + type);

        final File file = gameType.gameFile();
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

            final GsonBuilder builder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .serializeNulls();

            //TODO: register type adapters here!

            builder.registerTypeAdapter(
                PHASE_LIST_TYPE,
                new IdentifiableListDeserializer<AbstractGame, AbstractPhase>(gameInjector, builder, abstractGame, FEATURE_LIST_TYPE) {
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
                        return new IdentifiableListDeserializer<AbstractPhase, AbstractFeature>(gameInjector, builder, parent, CONDITION_LIST_TYPE) {
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
                                return new IdentifiableListDeserializer<AbstractFeature, AbstractVictoryCondition>(gameInjector, builder, parent, null) {
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
            );
            builder.registerTypeAdapter(abstractType, (InstanceCreator<AbstractGame>) t -> abstractGame);

            final Gson gson = builder.create();
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
            Optional<GameType> type = this.gameTypes.values().stream().filter(gameMode -> gameMode.name().equalsIgnoreCase(this.config.defaultGame())).findAny();
            if (type.isPresent()) {
                GameType gameType = type.get();
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
        Optional<Map.Entry<AbstractGamePlugin, GameType>> optional = this.gameTypes.entrySet().stream().filter(gType -> gType.getValue().type().equals(type)).findFirst();
        if (optional.isEmpty()) {
            throw new RuntimeException();
        }

        final Map.Entry<AbstractGamePlugin, GameType> entry = optional.get();
        final AbstractGamePlugin gamePlugin = entry.getKey();
        final GameType gameType = entry.getValue();
        final Injector injector = gamePlugin.injector();
        final GameInfo gameInfo = type.getAnnotation(GameInfo.class);
        final ResourceBundle.Control control = new FileJsonResourceLoader(gamePlugin, gameType.dataFolder());
        final MessageFormatTranslationProvider translationProvider = MessageFormatTranslationProvider
            .builder()
            .addBundle(ResourceBundle.getBundle(gameInfo.name(), Locale.ENGLISH, control))
            .build();
        final MiniMessageComponentRenderer renderer = new MiniMessageComponentRenderer(translationProvider);

        return injector.createChildInjector(new AbstractModule() {
            @Override
            protected void configure() {
                this.bind(MiniMessageComponentRenderer.class).toInstance(renderer);
            }
        }, new IdentifiableModule<>(Game.class, AbstractGame.class, type));
    }

    public <T extends AbstractPhase> T loadPhase(Injector injector, Class<? extends T> type, boolean create) {
        System.out.println("Loading Phase: " + type);
        injector.getParent();
        final AbstractGame game = injector.getInstance(AbstractGame.class);
        final Injector child = injector.createChildInjector(new IdentifiableModule<>(Phase.class, AbstractPhase.class, type));

        final T phase = child.getInstance(type);
        game.abstractPhases().add(phase);
        System.out.println("Loaded Phase: " + phase);

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

        final T feature = injector.getInstance(type);
        phase.abstractFeatures().add(feature);
        System.out.println("Loaded Feature: " + feature);

        return feature;
    }
}

package de.drolpi.gamecore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import de.drolpi.gamecore.api.game.GameController;
import de.drolpi.gamecore.api.game.GameControllerImpl;
import de.drolpi.gamecore.api.player.GamePlayerHandler;
import de.drolpi.gamecore.api.player.GamePlayerHandlerImpl;
import de.drolpi.gamecore.components.localization.MessageFormatTranslationProvider;
import de.drolpi.gamecore.components.localization.MessageFormatTranslationProviderImpl;
import de.drolpi.gamecore.components.localization.adventure.MiniMessageComponentRenderer;
import de.drolpi.gamecore.components.localization.adventure.MiniMessageTranslator;
import de.drolpi.gamecore.components.world.WorldHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class GameCoreModule extends AbstractModule {

    private final GameCorePlugin plugin;
    private final Gson gson;
    private final File dataFolder;

    public GameCoreModule(GameCorePlugin plugin, File dataFolder) {
        this.plugin = plugin;
        this.dataFolder = dataFolder;
        this.gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    }

    @Override
    protected void configure() {
        this.bind(Plugin.class).to(JavaPlugin.class);
        this.bind(JavaPlugin.class).to(GameCorePlugin.class);
        this.bind(GameCorePlugin.class).toInstance(this.plugin);
        this.bind(PluginManager.class).toInstance(this.plugin.getServer().getPluginManager());
        this.bind(GameCoreModule.class).toInstance(this);

        this.bind(Gson.class).toInstance(this.gson);

        this.bind(GameController.class).to(GameControllerImpl.class);
        this.bind(GamePlayerHandler.class).to(GamePlayerHandlerImpl.class);

        this.bind(File.class).annotatedWith(Names.named("DataFolder")).toInstance(this.dataFolder);
        this.bind(File.class).annotatedWith(Names.named("ConfigFile")).toInstance(new File(this.dataFolder, "config.json"));
        bind(File.class).annotatedWith(Names.named("WorldsFolder"))
            .toInstance(new File(this.plugin.getDataFolder(), "worlds"));
        bind(File.class).annotatedWith(Names.named("WorldContainer"))
            .toInstance(this.plugin.getServer().getWorldContainer().getAbsoluteFile());

    }
}

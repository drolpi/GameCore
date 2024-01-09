package de.drolpi.gamecore.components.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import de.drolpi.gamecore.components.world.map.DefinitionTypeAdapter;
import de.drolpi.gamecore.components.world.map.AbstractDefinition;
import de.drolpi.gamecore.components.world.map.GameMap;
import de.drolpi.gamecore.components.world.map.MapInfo;
import de.drolpi.gamecore.components.world.map.storage.JsonMapInfoStorage;
import de.drolpi.gamecore.components.world.map.storage.MapInfoStorage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class WorldHandler {

    private final MapInfoStorage mapInfoStorage;
    private final File worldsFolder;
    private final File worldContainer;
    private final Gson gson;
    private final List<GameMap> gameMaps = new ArrayList<>();
    private final List<String> loadedWorlds = new ArrayList<>();

    @Inject
    public WorldHandler(@Named("WorldsFolder") File worldsFolder, @Named("WorldContainer") File worldContainer) {
        this.worldsFolder = worldsFolder;
        this.worldContainer = worldContainer;
        this.mapInfoStorage = new JsonMapInfoStorage();
        this.mapInfoStorage.load();
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(AbstractDefinition.class, new DefinitionTypeAdapter())
            .create();
    }

    public void disable() {
        this.cleanup();
    }

    public Optional<MapInfo> getMapInfo(String name) {
        return this.mapInfoStorage.mapInfos().stream().filter(mapInfo -> mapInfo.name().equals(name)).findAny();
    }

    public GameMap loadMap(String name) {
        Optional<GameMap> map = this.getMap(name);

        if (map.isPresent()) {
            return map.get();
        }

        Optional<MapInfo> mapInfo = this.getMapInfo(name);

        if (mapInfo.isEmpty()) {
            throw new RuntimeException();
        }

        File worldFolder = new File(this.worldsFolder, mapInfo.get().worldName());
        File configFile = new File(worldFolder, "config.json");

        try (JsonReader reader = new JsonReader(new FileReader(configFile))) {
            GameMap loaded = this.gson.fromJson(reader, GameMap.class);

            this.gameMaps.add(loaded);
            return loaded;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<GameMap> getMap(String name) {
        return this.gameMaps.stream().filter(gameMap -> gameMap.name().equals(name)).findAny();
    }

    public void saveMap(GameMap gameMap) {
        Optional<MapInfo> mapInfo = this.mapInfoStorage.mapInfos().stream().filter(info -> info.worldName().equals(gameMap.name())).findAny();

        if (mapInfo.isEmpty()) {
            throw new RuntimeException();
        }

        File worldFolder = new File(this.worldsFolder, mapInfo.get().worldName());
        File configFile = new File(worldFolder, "config.json");

        try (FileWriter writer = new FileWriter(configFile)) {
            this.gson.toJson(gameMap, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public World loadWorld(GameMap gameMap) {
        //TODO: add game unique id
        String tempName = "TEMP_" + gameMap.name();
        if (this.isLoaded(gameMap)) {
            return Bukkit.getWorld(tempName);
        }

        File file = new File(this.worldContainer, tempName);
        File source = new File(this.worldsFolder, gameMap.name());

        try {
            this.copy(source, file);
            Files.deleteIfExists(new File(file, "uid.dat").toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        World world = this.loadLocalWorld(tempName);
        this.loadedWorlds.add(tempName);
        return world;
    }

    public World loadLocalWorld(String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.environment(World.Environment.NORMAL); //TODO do we need support for environment in maps?
        wc.generateStructures(false);
        wc.type(WorldType.NORMAL);
        //wc.generator(new CleanRoomChunkGenerator());
        wc.generatorSettings("");
        World world = wc.createWorld();
        if (world == null) {
            throw new RuntimeException();
        }
        world.setKeepSpawnInMemory(false);
        world.setAutoSave(false);
        return world;
    }

    public void unloadWorld(GameMap gameMap) {
        String tempName = "TEMP_" + gameMap.name();
        boolean bool = this.unloadLocalWorld(tempName);

        if (!bool) {
            throw new RuntimeException();
        }
        this.loadedWorlds.remove(tempName);

        this.delete(new File(this.worldContainer, tempName));
    }

    public boolean unloadLocalWorld(String name) {
        return Bukkit.unloadWorld(name, false);
    }

    public boolean isLoaded(GameMap gameMap) {
        String tempName = "TEMP_" + gameMap.name();
        return this.loadedWorlds.contains(tempName);
    }

    private void cleanup() {
        File[] files = this.worldContainer.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.getName().startsWith("TEMP_")) {
                    this.delete(file);
                }
            }
        }
    }

    private void copy(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdir();
            }

            String[] files = source.list();
            if (files == null) return;
            for (String file : files) {
                File newSource = new File(source, file);
                File newDestination = new File(destination, file);
                copy(newSource, newDestination);
            }
        } else {
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
        }
    }

    public void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    delete(child);
                }
            }
        }

        file.delete();
    }
}

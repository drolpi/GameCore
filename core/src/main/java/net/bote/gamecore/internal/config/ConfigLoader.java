package net.bote.gamecore.internal.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

public class ConfigLoader {

    private final Gson gson;
    private final File configFile;

    private final GlobalConfig globalConfig;

    @Inject
    public ConfigLoader(Gson gson, @Named("ConfigFile") File configFile) {
        this.gson = gson;
        this.configFile = configFile;
        if (!configFile.exists()) {
            this.globalConfig = new GlobalConfig();
            this.save(configFile, this.globalConfig);
        } else {
            this.globalConfig = this.load(configFile, GlobalConfig.class);
        }
    }

    public <T> T load(File configFile, Class<T> type) {
        try (JsonReader reader = new JsonReader(new FileReader(configFile))) {
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save(File configFile, Object config) {
        if (!configFile.exists()) {
            try {
                if (configFile.getParentFile() != null) {
                    configFile.getParentFile().mkdirs();
                }
                configFile.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }

        try (Writer writer = new FileWriter(configFile, false)) {
            this.gson.toJson(config, writer);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public GlobalConfig globalConfig() {
        return this.globalConfig;
    }
}

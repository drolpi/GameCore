package de.drolpi.gamecore.internal.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ConfigLoader {

    private final Gson gson;

    private final GlobalConfig globalConfig;

    @Inject
    public ConfigLoader(Gson gson, @Named("ConfigFile") File configFile) {
        this.gson = gson;
        this.globalConfig = this.load(configFile, new GlobalConfig());
    }

    public <T> T load(File configFile, T defaultConfig) {
        if (!configFile.exists()) {
            this.save(configFile, defaultConfig);
            return defaultConfig;
        }

        try (JsonReader reader = new JsonReader(new FileReader(configFile))) {
            return gson.fromJson(reader, defaultConfig.getClass());
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

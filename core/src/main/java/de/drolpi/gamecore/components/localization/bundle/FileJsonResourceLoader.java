package de.drolpi.gamecore.components.localization.bundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class FileJsonResourceLoader extends ResourceBundle.Control {

    private static final String FORMAT_NAME = "natrox.json";

    private final File resourceDirectory;

    public FileJsonResourceLoader(File resourceDirectory) {
        this.resourceDirectory = resourceDirectory;
    }

    @Override
    public List<String> getFormats(String baseName) {
        List<String> list = new ArrayList<>();
        list.add(FORMAT_NAME);
        return list;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException {
        if (format.equals(FORMAT_NAME)) {
            String resName = this.toResourceName(this.toBundleName(baseName, locale), "json");
            File file = new File(this.resourceDirectory, resName);

            if (!file.exists()) {
                try (InputStream in = loader.getResourceAsStream(resName)) {
                    if (in == null) {
                        throw new IllegalArgumentException("Resource file " + resName + " does not exist");
                    }

                    try {
                        Files.copy(in, file.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return this.createBundle(in);
                    }
                }
            }

            try (InputStream fileIn = new FileInputStream(file)) {
                return this.createBundle(fileIn);
            }
        }
        return null;
    }

    @Override
    public long getTimeToLive(String baseName, Locale locale) {
        return TTL_NO_EXPIRATION_CONTROL;
    }

    private ResourceBundle createBundle(InputStream source) {
        Map<String, MessageFormat[]> entries = new HashMap<>();

        try (JsonReader reader = new JsonReader(new InputStreamReader(source,"UTF-8"))) {
            JsonElement el = JsonParser.parseReader(reader);
            if (!el.isJsonObject()) {
                throw new IllegalArgumentException("JSON resource files must have JSON object root");
            }

            for (Map.Entry<String, JsonElement> e : el.getAsJsonObject().entrySet()) {
                JsonElement value = e.getValue();
                if (value.isJsonArray()) {
                    JsonArray array = value.getAsJsonArray();
                    MessageFormat[] res = new MessageFormat[array.size()];
                    for (int i = 0; i < res.length; i++) {
                        res[i] = new MessageFormat(array.get(i).getAsString());
                    }
                    entries.put(e.getKey(), res);
                } else if (value.isJsonObject()) {
                    //logger.log(Level.WARNING, "Invalid value for key {0} in resource {1}",
                    //new Object[]{e.getKey(), resourceName});
                } else {
                    entries.put(e.getKey(), new MessageFormat[]{new MessageFormat(e.getValue().getAsString())});
                }
            }

            return new MapResourceBundle(entries);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package de.drolpi.gamecore.components.localization.bundle;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

public final class MapResourceBundle extends ResourceBundle {

    private final Map<String, MessageFormat[]> resources;

    public MapResourceBundle(Map<String, MessageFormat[]> resources) {
        this.resources = new HashMap<>(resources);
    }

    @Override
    protected Object handleGetObject(@NotNull String key) {
        return this.resources.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return new Vector<>(this.resources.keySet()).elements();
    }
}

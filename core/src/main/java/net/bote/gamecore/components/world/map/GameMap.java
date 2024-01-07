package net.bote.gamecore.components.world.map;

import java.util.HashMap;
import java.util.Map;

public class GameMap {

    private String name;
    private LocationDefinition center;
    private int radius;
    private final Map<String, AbstractDefinition> definitions = new HashMap<>();

    public String name() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    public <T> T definition(String name) {
        return (T) this.definitions.get(name);
    }

    public void addDefinition(String name, AbstractDefinition definition) {
        this.definitions.put(name, definition);
    }
}

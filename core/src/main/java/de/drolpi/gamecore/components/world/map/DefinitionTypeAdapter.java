package de.drolpi.gamecore.components.world.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DefinitionTypeAdapter implements JsonDeserializer<AbstractDefinition> {

    private final Gson gson;

    public DefinitionTypeAdapter() {
        this.gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbstractDefinition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String typeName = object.get("type").getAsString();
        try {
            Class<? extends AbstractDefinition> type = (Class<? extends AbstractDefinition>) Class.forName(typeName);

            return context.deserialize(object, type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

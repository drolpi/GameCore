package net.bote.gamecore.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.inject.Injector;
import net.bote.gamecore.api.AbstractIdentifiable;

import java.lang.reflect.Type;
import java.util.List;

public abstract class IdentifiableListDeserializer<T, U extends AbstractIdentifiable> implements JsonDeserializer<List<U>> {

    private final Injector parentInjector;
    private final T parent;
    private final Type childListType;

    public IdentifiableListDeserializer(Injector parentInjector, T parent, Type childListType) {
        this.parentInjector = parentInjector;
        this.parent = parent;
        this.childListType = childListType;
    }

    protected abstract U createChild(Injector parentInjector, T parent, Class<? extends U> childType);

    protected abstract List<U> getList(T parent);

    protected JsonDeserializer<?> next(U parent) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<U> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray array = json.getAsJsonArray();

        for (JsonElement element : array) {
            JsonObject object = element.getAsJsonObject();
            String typeName = object.get("type").getAsString();

            try {
                Class<? extends U> type = (Class<? extends U>) Class.forName(typeName);
                U child = this.createChild(parentInjector, parent, type);

                GsonBuilder builder = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .registerTypeAdapter(type, new InstanceCreator<U>() {
                        @Override
                        public U createInstance(Type t) {
                            return child;
                        }
                    });

                JsonDeserializer<?> typeAdapter = this.next(child);

                if (typeAdapter != null) {
                    builder.registerTypeAdapter(this.childListType, typeAdapter);
                }

                Gson gson = builder.create();

                gson.fromJson(object, type);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return this.getList(this.parent);
    }
}

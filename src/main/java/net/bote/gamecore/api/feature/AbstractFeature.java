package net.bote.gamecore.api.feature;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.game.GameInstance;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractFeature implements Feature {

    @Expose
    private final String type;

    public AbstractFeature() {
        this.type = this.getClass().getName().replace("FeatureTypeAdapter.DEFAULT_PATH" + ".", "");
    }

    public abstract void enable(@NotNull GameInstance gameInstance);

    public abstract void disable(@NotNull GameInstance gameInstance);

    @Override
    public @NotNull String type() {
        return this.type;
    }
}

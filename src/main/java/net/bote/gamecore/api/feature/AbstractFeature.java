package net.bote.gamecore.api.feature;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.GameInstanceService;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractFeature implements Feature, GameInstanceService {

    @Expose
    private final String type;

    public AbstractFeature() {
        this.type = this.getClass().getName().replace("FeatureTypeAdapter.DEFAULT_PATH" + ".", "");
    }

    @Override
    public @NotNull String type() {
        return this.type;
    }
}

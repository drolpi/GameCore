package net.bote.gamecore.api.condition;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.Creatable;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractVictoryCondition implements VictoryCondition, Creatable {

    @Expose
    private final String type;

    public AbstractVictoryCondition() {
        this.type = this.getClass().getName().replace("VictoryConditionTypeAdapter.DEFAULT_PATH" + ".", "");
    }

    @Override
    public @NotNull String type() {
        return this.type;
    }
}

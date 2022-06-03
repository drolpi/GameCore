package net.bote.gamecore.api.condition;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.game.GameInstance;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractVictoryCondition implements VictoryCondition {

    @Expose
    private final String type;

    public AbstractVictoryCondition() {
        this.type = this.getClass().getName().replace("VictoryConditionTypeAdapter.DEFAULT_PATH" + ".", "");
    }

    public abstract void create();

    public abstract void enable(@NotNull GameInstance gameInstance);

    @Override
    public @NotNull String type() {
        return this.type;
    }
}

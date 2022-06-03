package net.bote.gamecore.api.game;

import net.bote.gamecore.api.condition.VictoryCondition;
import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.feature.Feature;
import net.bote.gamecore.api.phase.Phase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGame implements Game {

    @Expose
    private final String type;

    @Expose
    private final List<Phase> phases;

    public AbstractGame() {
        this.type = this.getClass().getName().replace("GameTypeAdapter.DEFAULT_PATH" + ".", "");
        this.phases = new ArrayList<>();
    }

    public abstract void create();

    @Override
    public @NotNull String type() {
        return this.type;
    }

    @Override
    public @NotNull List<Phase> phases() {
        return this.phases;
    }
}

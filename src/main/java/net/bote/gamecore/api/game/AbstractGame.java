package net.bote.gamecore.api.game;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.Creatable;
import net.bote.gamecore.api.phase.Phase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGame implements Game, Creatable {

    @Expose
    private final String type;

    @Expose
    private final List<Phase> phases;

    public AbstractGame() {
        this.type = this.getClass().getName().replace("GameTypeAdapter.DEFAULT_PATH" + ".", "");
        this.phases = new ArrayList<>();
    }

    @Override
    public @NotNull String type() {
        return this.type;
    }

    @Override
    public @NotNull List<Phase> phases() {
        return this.phases;
    }
}

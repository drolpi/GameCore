package net.bote.gamecore.api.phase;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import com.google.inject.Injector;
import net.bote.gamecore.api.game.GameControllerImpl;
import net.bote.gamecore.GamePlugin;
import net.bote.gamecore.api.AbstractIdentifiable;
import net.bote.gamecore.api.Creatable;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.feature.Feature;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractPhase extends AbstractIdentifiable implements Phase, Creatable {

    @Inject
    private Injector injector;
    @Inject
    private GameControllerImpl gameController;
    @Inject
    private GamePlugin plugin;
    @Inject
    private PluginManager pluginManager;

    @Expose
    private boolean allowJoin = true;
    @Expose
    private boolean allowSpectate = false;
    @Expose
    private final List<AbstractFeature> features = new ArrayList<>();

    public void enable() {
        for (AbstractFeature feature : this.features) {
            feature.enable();
            this.pluginManager.registerEvents(feature, this.plugin);
        }
    }

    public void disable() {
        for (AbstractFeature feature : this.features) {
            feature.disable();
            HandlerList.unregisterAll(feature);
        }
    }

    public void setAllowJoin(boolean allowJoin) {
        this.allowJoin = allowJoin;
    }

    public boolean allowJoin() {
        return this.allowJoin;
    }

    public void setAllowSpectate(boolean allowSpectate) {
        this.allowSpectate = allowSpectate;
    }

    public boolean allowSpectate() {
        return this.allowSpectate;
    }

    public <T extends AbstractFeature> T createFeature(Class<T> type) {
        return this.gameController.loadFeature(this.injector, type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractFeature> T feature(Class<T> type) {
        for (AbstractFeature feature : this.features) {
            if (feature.getClass().equals(type)) {
                return (T) feature;
            }
        }

        throw new RuntimeException();
    }

    @Override
    public @NotNull Set<Feature> features() {
        return new HashSet<>(this.features);
    }

    public List<AbstractFeature> abstractFeatures() {
        return this.features;
    }

    public Injector injector() {
        return this.injector;
    }
}

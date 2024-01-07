package net.bote.gamecore.api.feature;

import com.google.inject.Singleton;
import net.bote.gamecore.api.AbstractIdentifiable;
import org.bukkit.event.Listener;

@Singleton
public abstract class AbstractFeature extends AbstractIdentifiable implements Feature, Listener {

    public AbstractFeature() {

    }

    public abstract void enable();

    public abstract void disable();
}

package net.bote.gamecore.api.feature.def;

import net.bote.gamecore.api.eventbus.EventBus;
import net.bote.gamecore.api.eventbus.EventListener;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.feature.FeatureInfo;
import net.bote.gamecore.api.game.GameInstance;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

@FeatureInfo(name = "AutoRespawnFeature", description = "Makes dead players respawn automatically", version = "1.0", authors = "dasdrolpi")
public class AutoRespawnFeature extends AbstractFeature {

    private final EventListener<PlayerDeathEvent> deathListener;

    public AutoRespawnFeature() {
        this.deathListener = EventListener.of(PlayerDeathEvent.class, event -> event.getEntity().spigot().respawn());
    }

    @Override
    public void enable(@NotNull GameInstance gameInstance) {
        EventBus eventBus = gameInstance.eventBus();

        eventBus.register(this.deathListener);
    }

    @Override
    public void disable(@NotNull GameInstance gameInstance) {
        EventBus eventBus = gameInstance.eventBus();

        eventBus.unregister(this.deathListener);
    }
}

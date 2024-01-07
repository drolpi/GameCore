package net.bote.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import net.bote.gamecore.api.feature.AbstractFeature;
import net.bote.gamecore.api.phase.Phase;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

public class NoWeatherChangeFeature extends AbstractFeature {

    private final MapFeature mapFeature;

    @Expose
    private WeatherType weatherType = WeatherType.CLEAR;

    private World world;

    @Inject
    public NoWeatherChangeFeature(Phase phase) {
        this.mapFeature = phase.feature(MapFeature.class);
    }

    @Override
    public void enable() {
        this.world = this.mapFeature.world();
        switch (this.weatherType) {
            case CLEAR -> {
                this.world.setStorm(false);
                this.world.setThundering(false);
            }
            case DOWNFALL -> {
                this.world.setStorm(true);
                this.world.setThundering(true);
            }
        }
    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void handle(WeatherChangeEvent event) {
        if (!event.getWorld().equals(this.world)) {
            return;
        }
        event.setCancelled(true);
    }
}

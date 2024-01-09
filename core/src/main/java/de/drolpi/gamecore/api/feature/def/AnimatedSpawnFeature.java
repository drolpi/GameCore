package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.components.world.map.GameMap;
import de.drolpi.gamecore.components.world.map.LocationDefinition;
import de.drolpi.gamecore.components.world.map.LocationsDefinition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatedSpawnFeature extends AbstractFeature implements Runnable {

    private final GamePlugin plugin;
    private final BukkitScheduler scheduler;
    private final MapFeature mapFeature;

    @Expose
    private final Material[] blocks = {
        Material.WHITE_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA,
        Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA,
        Material.LIGHT_GRAY_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA,
        Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA
    };

    private Map<Location, List<Location>> locations;
    private BukkitTask task;
    private int count;
    private int color;

    @Inject
    public AnimatedSpawnFeature(GamePlugin plugin, Phase phase) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
        this.mapFeature = phase.feature(MapFeature.class);
    }

    @Override
    public void enable() {
        this.locations = new HashMap<>();
        final GameMap gameMap = this.mapFeature.gameMap();
        final LocationsDefinition definition = gameMap.definition("Spawns");

        for (LocationDefinition location : definition.locations()) {
            final Location center = location.toLocation(this.mapFeature.world()).subtract(0, 1, 0);
            final List<Location> blocks = new ArrayList<>();

            blocks.add(center.clone().add(1, 0, -1));
            blocks.add(center.clone().add(0, 0, -1));
            blocks.add(center.clone().add(-1, 0, -1));
            blocks.add(center.clone().add(-1, 0, 0));
            blocks.add(center.clone().add(-1, 0, 1));
            blocks.add(center.clone().add(0, 0, 1));
            blocks.add(center.clone().add(1, 0, 1));
            blocks.add(center.clone().add(1, 0, 0));

            this.locations.put(center, blocks);
        }

        this.task = this.scheduler.runTaskTimer(this.plugin, this, 0, 5);
    }

    @Override
    public void disable() {
        this.task.cancel();
        this.task = null;
        this.locations = null;
        this.count = 0;
        this.color = 0;
    }

    @Override
    public void run() {
        this.count++;

        if (this.count > 7) {
            this.count = 0;
            this.color++;
        }

        if (this.color >= 15) {
            this.color = 0;
        }

        for (Map.Entry<Location, List<Location>> entry : this.locations.entrySet()) {
            entry.getValue().get(this.count).getBlock().setType(this.blocks[this.color]);
        }
    }
}

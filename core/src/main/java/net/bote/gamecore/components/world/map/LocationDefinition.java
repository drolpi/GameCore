package net.bote.gamecore.components.world.map;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationDefinition extends AbstractDefinition {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public LocationDefinition(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
    }
}

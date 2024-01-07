package net.bote.gamecore.components.world.map;

import java.util.ArrayList;
import java.util.List;

public class LocationsDefinition extends AbstractDefinition {

    private final List<LocationDefinition> locations = new ArrayList<>();

    public List<LocationDefinition> locations() {
        return this.locations;
    }
}

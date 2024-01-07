package de.drolpi.gamecore.api.player;

import java.util.UUID;

public interface GamePlayerHandler {

    GamePlayer player(UUID uniqueId);

}

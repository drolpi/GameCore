package de.drolpi.gamecore;

import de.drolpi.gamecore.api.game.GameController;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface GamePlugin extends Plugin  {

    @NotNull GameController gameController();

}

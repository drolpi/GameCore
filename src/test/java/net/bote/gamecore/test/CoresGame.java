package net.bote.gamecore.test;

import com.google.gson.annotations.Expose;
import net.bote.gamecore.api.condition.def.TestVictoryCondition;
import net.bote.gamecore.api.feature.def.GameModeFeature;
import net.bote.gamecore.api.game.AbstractGame;
import net.bote.gamecore.api.game.GameInfo;
import org.bukkit.GameMode;

@GameInfo(name = "CoresGame", description = "The Cores Game", version = "2.0.0-SNAPSHOT", authors = {"bote100", "dasdrolpi"})
final class CoresGame extends AbstractGame {

    @Expose
    private int value;

    public CoresGame() {

    }

    @Override
    public void create() {
        this.value = 100;
    }
}

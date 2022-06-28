import net.bote.gamecore.api.condition.VictoryCondition;
import net.bote.gamecore.api.condition.def.TestVictoryCondition;
import net.bote.gamecore.api.feature.def.GameModeFeature;
import net.bote.gamecore.api.phase.AbstractPhase;
import org.bukkit.GameMode;

final class InGamePhase extends AbstractPhase {

    public InGamePhase() {

    }

    @Override
    public void create() {
        this.setAllowJoin(false);
        this.setAllowSpectate(true);

        GameModeFeature gameModeFeature = this.addFeature(GameModeFeature.class);
        gameModeFeature.setGameMode(GameMode.SURVIVAL);

        VictoryCondition victoryCondition = this.addVictoryCondition(TestVictoryCondition.class);
    }
}

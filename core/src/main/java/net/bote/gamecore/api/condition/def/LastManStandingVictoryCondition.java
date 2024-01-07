package net.bote.gamecore.api.condition.def;

import com.google.inject.Inject;
import net.bote.gamecore.api.condition.AbstractVictoryCondition;
import net.bote.gamecore.api.event.GamePostLeaveEvent;
import net.bote.gamecore.api.feature.def.TeamFeature;
import net.bote.gamecore.api.feature.def.TeamSelectFeature;
import net.bote.gamecore.api.feature.def.WinDetectionFeature;
import net.bote.gamecore.api.phase.AbstractPhase;
import net.bote.gamecore.api.player.GamePlayerHandler;
import net.bote.gamecore.components.team.TeamInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Optional;

public class LastManStandingVictoryCondition extends AbstractVictoryCondition {

    private final GamePlayerHandler playerHandler;
    private final TeamFeature teamFeature;
    private final WinDetectionFeature winDetectionFeature;

    @Inject
    public LastManStandingVictoryCondition(GamePlayerHandler playerHandler, AbstractPhase phase) {
        this.playerHandler = playerHandler;
        this.teamFeature = phase.feature(TeamFeature.class);
        this.winDetectionFeature = phase.feature(WinDetectionFeature.class);
    }

    @Override
    public void create() {

    }

    @Override
    public boolean completed() {
        return super.completed() || this.teamFeature.aliveTeamCount() == 0;
    }

    @Override
    public TeamInstance condition() {
        Optional<TeamInstance> optional = this.teamFeature.aliveTeams().stream().findFirst();

        if (optional.isEmpty()) {
            return null;
        }

        return optional.get();
    }

    @EventHandler
    public void handle(EntityDeathEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) {
            return;
        }

        if(!(event.getEntity() instanceof Player player)) {
            return;
        }

        this.teamFeature.removePlayer(this.playerHandler.player(event.getEntity().getUniqueId()));
        this.winDetectionFeature.checkWin(this);
    }

    @EventHandler
    public void handle(GamePostLeaveEvent event) {
        this.winDetectionFeature.checkWin(this);
    }
}

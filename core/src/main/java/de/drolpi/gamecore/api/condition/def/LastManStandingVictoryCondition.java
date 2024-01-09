package de.drolpi.gamecore.api.condition.def;

import com.google.inject.Inject;
import de.drolpi.gamecore.api.condition.AbstractVictoryCondition;
import de.drolpi.gamecore.api.event.GamePostLeaveEvent;
import de.drolpi.gamecore.api.feature.def.TeamFeature;
import de.drolpi.gamecore.api.feature.def.WinDetectionFeature;
import de.drolpi.gamecore.api.phase.AbstractPhase;
import de.drolpi.gamecore.api.player.GamePlayerHandler;
import de.drolpi.gamecore.components.team.TeamInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(EntityDeathEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) {
            return;
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!this.teamFeature.isInTeam(player.getUniqueId())) {
            return;
        }

        this.teamFeature.removePlayer(this.playerHandler.player(player.getUniqueId()));
        this.winDetectionFeature.checkWin(this);
    }

    @EventHandler
    public void handle(GamePostLeaveEvent event) {
        if (!this.teamFeature.isInTeam(event.gamePlayer().uniqueId())) {
            return;
        }

        this.winDetectionFeature.checkWin(this);
    }
}

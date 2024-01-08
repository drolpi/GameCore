package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.components.team.TeamInstance;
import de.drolpi.gamecore.api.condition.WinnerGameData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;

import java.util.stream.Collectors;

public class PostGameFeature extends AbstractFeature {

    private final Game game;
    private final Phase phase;

    @Expose
    private String emptyTeamName = "§c???";
    @Expose
    private String emptyPlayerName = "§c???";

    @Inject
    public PostGameFeature(Game game, Phase phase) {
        this.game = game;
        this.phase = phase;
    }

    @Override
    public void enable() {
        WinnerGameData gameData = this.game.gameData(WinnerGameData.class).orElse(new WinnerGameData());

        String teamName = this.emptyTeamName;
        String playerName = this.emptyPlayerName;

        if (gameData.winner != null) {
            TeamInstance winner = gameData.winner;

            teamName = winner.color().chatColor() + winner.name();
            playerName = winner.color().chatColor() + winner.players().collect().stream().map(gamePlayer -> gamePlayer.player().getName()).collect(Collectors.joining(","));


            for (GamePlayer player : winner.players().collect()) {
                winner.players().remove(player);
            }
        }

        final TagResolver.Single teamNameHolder = Placeholder.component("teamname", Component.text(teamName));
        final TagResolver.Single playerNamesHolder = Placeholder.component("playernames", Component.text(playerName));

        final Component message = Component.translatable(this.phase.key() + "winner_brodcast");
        final Title title = Title.title(
            Component.translatable(this.phase.key() + "winner_title"),
            Component.translatable(this.phase.key() + "winner_subtitle")
        );

        for (final GamePlayer allPlayer : this.game.allPlayers()) {
            allPlayer.showTitle(title, teamNameHolder, playerNamesHolder);
            allPlayer.sendMessage(message, teamNameHolder, playerNamesHolder);
        }
    }

    @Override
    public void disable() {

    }
}

package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.components.team.TeamInstance;
import de.drolpi.gamecore.api.condition.WinnerGameData;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class PostGameFeature extends AbstractFeature {

    private final Game game;

    @Expose
    private String emptyTeamName = "§c???";
    @Expose
    private String emptyPlayerName = "§c???";

    @Inject
    public PostGameFeature(Game game) {
        this.game = game;
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

        for (GamePlayer gamePlayer : this.game.allPlayers()) {
            Player player = gamePlayer.player();

            player.sendMessage(Component.text(teamName));
            player.sendMessage(Component.text(playerName));
        }
    }

    @Override
    public void disable() {

    }
}

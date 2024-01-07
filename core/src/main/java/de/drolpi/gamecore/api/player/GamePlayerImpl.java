package de.drolpi.gamecore.api.player;

import de.drolpi.gamecore.api.game.AbstractGame;
import de.drolpi.gamecore.api.game.GameControllerImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

final class GamePlayerImpl extends PreGamePlayerImpl implements GamePlayer {

    private final Player player;
    private final Locale locale = Locale.ENGLISH;
    private final GameControllerImpl gameController;

    GamePlayerImpl(Player player, GameControllerImpl gameController) {
        super(player.getUniqueId());
        this.player = player;
        this.gameController = gameController;
    }

    GamePlayerImpl(PreGamePlayerImpl gamePlayer, Player player, GameControllerImpl gameController) {
        super(gamePlayer.uniqueId);
        this.player = player;
        this.gameController = gameController;
    }

    @Override
    public Player player() {
        return this.player;
    }

    @Override
    public void sendMessage(Component component, TagResolver... resolvers) {
        Set<AbstractGame> game = this.gameController.abstractGames(this, true);
        Optional<AbstractGame> optional = game.stream().findFirst();
        if (optional.isEmpty()) {
            throw new RuntimeException();
        }

        this.player.sendMessage(optional.get().renderer().render(component, this.locale, resolvers));
    }
}

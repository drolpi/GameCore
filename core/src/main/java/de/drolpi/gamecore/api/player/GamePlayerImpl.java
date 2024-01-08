package de.drolpi.gamecore.api.player;

import de.drolpi.gamecore.api.game.AbstractGame;
import de.drolpi.gamecore.api.game.GameControllerImpl;
import de.drolpi.gamecore.components.localization.adventure.MiniMessageComponentRenderer;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

final class GamePlayerImpl extends PreGamePlayerImpl implements GamePlayer {

    private final Player player;
    private final Locale locale = Locale.GERMAN;
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

    @Override
    public void showTitle(Title title, TagResolver... resolvers) {
        Set<AbstractGame> game = this.gameController.abstractGames(this, true);
        Optional<AbstractGame> optional = game.stream().findFirst();
        if (optional.isEmpty()) {
            throw new RuntimeException();
        }

        MiniMessageComponentRenderer renderer = optional.get().renderer();
        Component head = renderer.render(title.title(), this.locale, resolvers);
        Component sub = renderer.render(title.subtitle(), this.locale, resolvers);
        Title result = Title.title(head, sub, title.times());

        this.player.showTitle(result);
    }

    @Override
    public void playSound(Sound sound) {
        this.player.playSound(sound);
    }
}

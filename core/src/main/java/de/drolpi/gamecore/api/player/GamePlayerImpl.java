package de.drolpi.gamecore.api.player;

import de.drolpi.gamecore.api.feature.def.BossBarFeature;
import de.drolpi.gamecore.api.game.AbstractGame;
import de.drolpi.gamecore.api.game.GameControllerImpl;
import de.drolpi.gamecore.components.localization.adventure.MiniMessageComponentRenderer;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

final class GamePlayerImpl extends PreGamePlayerImpl implements GamePlayer {

    private final Player player;
    private final Locale locale = Locale.GERMAN;
    private final GameControllerImpl gameController;

    private final Map<BossBar, BossBarCopyListener> shownBossBars;

    GamePlayerImpl(Player player, GameControllerImpl gameController) {
        super(player.getUniqueId());
        this.player = player;
        this.gameController = gameController;
        this.shownBossBars = new HashMap<>();
    }

    GamePlayerImpl(PreGamePlayerImpl gamePlayer, Player player, GameControllerImpl gameController) {
        super(gamePlayer.uniqueId);
        this.player = player;
        this.gameController = gameController;
        this.shownBossBars = new HashMap<>();
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
    public void showBossBar(BossBar bossBar, TagResolver... resolvers) {
        Set<AbstractGame> game = this.gameController.abstractGames(this, true);
        Optional<AbstractGame> optional = game.stream().findFirst();
        if (optional.isEmpty()) {
            throw new RuntimeException();
        }

        MiniMessageComponentRenderer renderer = optional.get().renderer();
        Component name = renderer.render(bossBar.name(), this.locale, resolvers);
        BossBar result = BossBar.bossBar(name, bossBar.progress(), bossBar.color(), bossBar.overlay());
        BossBarCopyListener copyListener = new BossBarCopyListener(result, renderer, locale, resolvers);
        bossBar.addListener(copyListener);
        this.shownBossBars.put(bossBar, copyListener);
        this.player.showBossBar(result);
    }

    @Override
    public void hideBossBar(BossBar bossBar) {
        BossBarCopyListener listener = this.shownBossBars.get(bossBar);
        if (listener == null) return;
        bossBar.removeListener(listener);
        this.shownBossBars.remove(bossBar);
        this.player.hideBossBar(listener.copy());
    }

    @Override
    public void playSound(Sound sound) {
        this.player.playSound(sound);
    }

    private record BossBarCopyListener(BossBar copy, MiniMessageComponentRenderer renderer, Locale locale,
                                       TagResolver... resolvers) implements BossBar.Listener {

        @Override
            public void bossBarNameChanged(@NotNull BossBar bossBar, @NotNull Component oldName, @NotNull Component newName) {
                this.copy.name(this.renderer.render(newName, this.locale, this.resolvers));
            }

        @Override
            public void bossBarProgressChanged(@NotNull BossBar bossBar, float oldProgress, float newProgress) {
                this.copy.progress(newProgress);
            }

        @Override
            public void bossBarColorChanged(@NotNull BossBar bossBar, BossBar.@NotNull Color oldColor, BossBar.@NotNull Color newColor) {
                this.copy.color(newColor);
            }

        @Override
            public void bossBarOverlayChanged(@NotNull BossBar bossBar, BossBar.@NotNull Overlay oldOverlay, BossBar.@NotNull Overlay newOverlay) {
                this.copy.overlay(newOverlay);
            }
        }
}

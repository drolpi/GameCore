package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.event.GamePostLeaveEvent;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;

public class BossBarFeature extends AbstractFeature {

    private final Game game;

    @Expose
    private BossBar.Color color = BossBar.Color.BLUE;
    @Expose
    private BossBar.Overlay overlay = BossBar.Overlay.PROGRESS;

    private final BossBar bossBar;
    private final Component defaultName;
    private TagResolver[] resolvers;

    @Inject
    public BossBarFeature(Game game, Phase phase) {
        this.game = game;
        this.defaultName = Component.translatable(phase.key() + "bossbar_name");
        this.bossBar = BossBar.bossBar(this.defaultName, 1.0f, this.color, this.overlay);
        this.resolvers = new TagResolver[]{};
    }

    @Override
    public void enable() {

        for (GamePlayer gamePlayer : this.game.players()) {
            gamePlayer.showBossBar(this.bossBar, resolvers);
        }
    }

    @Override
    public void disable() {
        for (GamePlayer gamePlayer : this.game.players()) {
            gamePlayer.hideBossBar(this.bossBar);
        }
    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        event.gamePlayer().showBossBar(this.bossBar);
    }

    @EventHandler
    public void handle(GamePostLeaveEvent event) {
        event.gamePlayer().hideBossBar(this.bossBar);
    }

    public BossBar bossBar() {
        return this.bossBar;
    }

    public BossBar.Color color() {
        return this.color;
    }

    public BossBar.Overlay overlay() {
        return this.overlay;
    }

    public void setProgress(float progress) {
        this.bossBar.progress(progress);
    }

    public void setName(Component name) {
        this.bossBar.name(name);
    }

    public void resetName() {
        this.setName(this.defaultName);
    }

    public void setColor(BossBar.Color color) {
        this.bossBar.color(color);
    }

    public void setResolvers(TagResolver[] resolvers) {
        this.resolvers = resolvers;
    }

    public void setOverlay(BossBar.Overlay overlay) {
        this.overlay = overlay;
    }
}

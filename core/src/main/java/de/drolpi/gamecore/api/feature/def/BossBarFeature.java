package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.phase.Phase;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.event.GamePostLeaveEvent;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class BossBarFeature extends AbstractFeature {

    private final Game game;

    private final Map<BossBar, BossBarInfo> bossBars;
    private final Component defaultName;

    @Inject
    public BossBarFeature(Game game, Phase phase) {
        this.game = game;
        this.defaultName = Component.translatable(phase.key() + "bossbar_name");
        this.bossBars = new HashMap<>();
    }

    @Override
    public void enable() {
        for (GamePlayer gamePlayer : this.game.players()) {
            for (BossBarInfo info : bossBars.values()) {
                gamePlayer.showBossBar(info.bossbar, info.resolvers);
            }
        }
    }

    @Override
    public void disable() {
        for (GamePlayer gamePlayer : this.game.players()) {
            for (BossBar bossBar : bossBars.keySet()) {
                gamePlayer.hideBossBar(bossBar);
            }
        }
    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        for (BossBarInfo info : bossBars.values()) {
            event.gamePlayer().showBossBar(info.bossbar, info.resolvers);
        }
    }

    @EventHandler
    public void handle(GamePostLeaveEvent event) {
        for (BossBar bossBar : bossBars.keySet()) {
            event.gamePlayer().hideBossBar(bossBar);
        }
    }

    public BossBar.Color color(BossBar bossBar) {
        return this.bossBars.get(bossBar).color;
    }

    public BossBar.Overlay overlay(BossBar bossBar) {
        return this.bossBars.get(bossBar).overlay;
    }

    public void setProgress(BossBar bossBar, float progress) {
        this.bossBars.get(bossBar).progress(progress);
    }

    public void setName(BossBar bossBar, Component name) {
        this.bossBars.get(bossBar).name(name);
    }

    public void resetName(BossBar bossBar) {
        this.bossBars.get(bossBar).name(this.defaultName);
    }

    public void setColor(BossBar bossBar, BossBar.Color color) {
        this.bossBars.get(bossBar).color(color);
    }

    public void setResolvers(BossBar bossbar, TagResolver... resolvers) {
        this.bossBars.get(bossbar).resolvers = resolvers;
    }

    public void setOverlay(BossBar bossBar, BossBar.Overlay overlay) {
        this.bossBars.get(bossBar).overlay(overlay);
    }

    public void addBossBar(BossBar bossBar) {
        this.bossBars.put(bossBar, new BossBarInfo(bossBar.color(), bossBar.overlay(), bossBar));
    }

    public void removeBossBar(BossBar bossBar) {
        this.bossBars.remove(bossBar);
    }

    public static class BossBarInfo {

        @Expose
        private BossBar.Color color;
        @Expose
        private BossBar.Overlay overlay;
        @Expose
        private TagResolver[] resolvers;

        private final BossBar bossbar;

        public BossBarInfo(BossBar.Color color, BossBar.Overlay overlay, BossBar bossbar) {
            this.color = color;
            this.overlay = overlay;
            this.bossbar = bossbar;
            this.resolvers = new TagResolver[]{};
        }

        public BossBar.Color color() {
            return this.color;
        }

        public BossBar.Overlay overlay() {
            return this.overlay;
        }

        public BossBar bossbar() {
            return this.bossbar;
        }

        public TagResolver[] resolvers() {
            return this.resolvers;
        }

        public Component name() {
            return this.bossbar.name();
        }

        public void progress(float progress) {
            this.bossbar.progress(progress);
        }

        public void name(Component name) {
            this.bossbar.name(name);
        }

        public void color(BossBar.Color color) {
            this.color = color;
        }

        public void overlay(BossBar.Overlay overlay) {
            this.overlay = overlay;
        }

        public void resolvers(TagResolver[] resolvers) {
            this.resolvers = resolvers;
        }
    }
}

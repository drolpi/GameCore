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

import java.util.*;

public class BossBarFeature extends AbstractFeature {

    private final Game game;

    @Expose
    private final List<BossBarInfo> bossBarInfos;

    private final Map<String, BossBarContainer> bossBars;

    private final Phase phase;


    @Inject
    public BossBarFeature(Game game, Phase phase) {
        this.game = game;
        this.bossBars = new HashMap<>();
        this.bossBarInfos = new ArrayList<>();
        this.phase = phase;
    }

    @Override
    public void enable() {
        for (BossBarInfo info : this.bossBarInfos) {
            this.showForAll(info.id);
        }
    }

    @Override
    public void disable() {
        for (BossBarInfo info : this.bossBarInfos) {
            this.hideForAll(info.id);
        }
    }

    public void register(String id, BossBar.Color color, BossBar.Overlay overlay) {
        this.bossBarInfos.add(new BossBarInfo(id, color, overlay));
        this.generateContainer(id);
        this.showForAll(id);
    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        for (BossBarInfo info : this.bossBarInfos) {
            BossBarContainer container = this.getContainer(info.id);
            if (container.hidden)
                continue;
            event.gamePlayer().showBossBar(info.id, container.bossBar, container.resolvers);
        }
    }

    @EventHandler
    public void handle(GamePostLeaveEvent event) {

    }

    private void showForAll(String id) {
        BossBarContainer container = this.getContainer(id);
        for (GamePlayer gamePlayer : this.game.players()) {
            gamePlayer.showBossBar(id, container.bossBar);
        }
    }

    private void hideForAll(String id) {
        BossBarContainer container = this.getContainer(id);
        for (GamePlayer gamePlayer : this.game.players()) {
            gamePlayer.hideBossBar(id, container.bossBar);
        }
    }

    public boolean hidden(String id) {
        return this.getContainer(id).hidden;
    }

    public void setHidden(String id, boolean hidden) {
        this.getContainer(id).hidden = hidden;
        if (hidden) {
            hideForAll(id);
            return;
        }
        showForAll(id);
    }

    public boolean isRegistered(String id) {
        return this.getInfo(id) != null;
    }

    public BossBar.Color color(String id) {
        return this.getInfo(id).color;
    }

    public BossBar.Overlay overlay(String id) {
        return this.getInfo(id).overlay;
    }

    public float progress(String id) {
        return this.getContainer(id).bossBar.progress();
    }

    public void setProgress(String id, float progress) {
        this.getContainer(id).bossBar.progress(progress);
    }

    public void setColor(String id, BossBar.Color color) {
        this.getInfo(id).color = color;
        this.getContainer(id).bossBar.color(color);
    }

    public void setOverlay(String id, BossBar.Overlay overlay) {
        this.getInfo(id).overlay = overlay;
        this.getContainer(id).bossBar.overlay(overlay);
    }

    public void setName(String id, Component name) {
        this.getContainer(id).bossBar.name(name);
    }

    public void setResolvers(String id, TagResolver... resolvers) {
        this.getContainer(id).resolvers = resolvers;
    }

    public void resetName(String id) {
        this.getContainer(id).bossBar.name(BossBarContainer.name(this.phase.key(), id));
    }

    public BossBarInfo getInfo(String id) {
        Optional<BossBarInfo> bossBarInfo = this.bossBarInfos.stream().filter((info) -> info.id.equals(id)).findFirst();
        return bossBarInfo.orElse(null);
    }

    private BossBarContainer getContainer(String id) {
        BossBarContainer container = this.bossBars.get(id);
        if (container != null) return container;
        return this.generateContainer(id);

    }

    private BossBarContainer generateContainer(String id) {
        System.out.println("Generating container");
        BossBarInfo info = this.getInfo(id);
        System.out.println("for info: "+info);
        BossBarContainer container = new BossBarContainer(info, this.phase.key());
        this.bossBars.put(id, container);
        return container;
    }

    private static class BossBarInfo {

        @Expose
        private final String id;
        @Expose
        private BossBar.Color color;
        @Expose
        private BossBar.Overlay overlay;

        private BossBarInfo(String id, BossBar.Color color, BossBar.Overlay overlay) {
            this.id = id;
            this.color = color;
            this.overlay = overlay;
        }
    }
    
    private static class BossBarContainer {
        
        private final BossBar bossBar;
        private TagResolver[] resolvers;
        private boolean hidden = false;

        private BossBarContainer(BossBarInfo info, String phaseId) {
            this.bossBar = BossBar.bossBar(BossBarContainer.name(phaseId, info.id), 1.0F, info.color, info.overlay);
        }

        private static Component name(String phaseId, String bossBarId) {
            return Component.translatable(phaseId + "bossbar." + bossBarId);
        }
    }
}

package de.drolpi.gamecore.api.feature.def;

import com.google.gson.annotations.Expose;
import com.google.inject.Inject;
import de.drolpi.gamecore.api.player.GamePlayer;
import de.drolpi.gamecore.GamePlugin;
import de.drolpi.gamecore.api.event.GameJoinEvent;
import de.drolpi.gamecore.api.event.GamePostLeaveEvent;
import de.drolpi.gamecore.api.feature.AbstractFeature;
import de.drolpi.gamecore.api.game.Game;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;

public class BossBarFeature extends AbstractFeature {

    private final GamePlugin plugin;
    private final Game game;

    //TODO: Lang
    @Expose
    private String message = "";
    @Expose
    private BarColor color = BarColor.BLUE;
    @Expose
    private BarStyle style = BarStyle.SEGMENTED_20;

    private BossBar bossBar;

    @Inject
    public BossBarFeature(GamePlugin plugin, Game game) {
        this.plugin = plugin;
        this.game = game;
    }

    @Override
    public void enable() {
        this.bossBar = this.plugin.getServer().createBossBar(this.message, this.color, this.style);
        if (this.message.isBlank()) {
            this.bossBar.setVisible(false);
        }

        for (GamePlayer gamePlayer : this.game.players()) {
            this.bossBar.addPlayer(gamePlayer.player());
        }
    }

    @Override
    public void disable() {
        this.bossBar.removeAll();
    }

    @EventHandler
    public void handle(GameJoinEvent event) {
        this.bossBar.addPlayer(event.gamePlayer().player());
    }

    @EventHandler
    public void handle(GamePostLeaveEvent event) {
        this.bossBar.removePlayer(event.gamePlayer().player());
    }

    public BossBar bossBar() {
        return this.bossBar;
    }

    public String message() {
        return this.message;
    }

    public BarColor color() {
        return this.color;
    }

    public BarStyle style() {
        return this.style;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setColor(BarColor color) {
        this.color = color;
    }

    public void setStyle(BarStyle style) {
        this.style = style;
    }
}

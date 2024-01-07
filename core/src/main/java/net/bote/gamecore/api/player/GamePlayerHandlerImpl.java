package net.bote.gamecore.api.player;

import com.google.inject.Singleton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class GamePlayerHandlerImpl implements GamePlayerHandler {

    private final Map<UUID, GamePlayer> playerStorage = new HashMap<>();
    private final Map<UUID, PreGamePlayerImpl> tempStorage = new ConcurrentHashMap<>();

    public boolean login(UUID uniqueId) {
        PreGamePlayerImpl gamePlayer = new PreGamePlayerImpl(uniqueId);
        this.tempStorage.put(uniqueId, gamePlayer);
        return true;
    }

    public void join(Player player) {
        if(!this.hasLoggedIn(player.getUniqueId())) {
            throw new RuntimeException();
        }

        final PreGamePlayerImpl preGamePlayer = this.tempStorage.remove(player.getUniqueId());
        final GamePlayerImpl gamePlayer = new GamePlayerImpl(preGamePlayer, player);

        this.playerStorage.put(player.getUniqueId(), gamePlayer);
    }

    public void quit(Player player) {
        this.tempStorage.remove(player.getUniqueId());
        this.playerStorage.remove(player.getUniqueId());
    }

    public boolean hasLoggedIn(UUID uniqueId) {
        return this.tempStorage.containsKey(uniqueId);
    }

    @Override
    public GamePlayer player(UUID uniqueId) {
        return this.playerStorage.get(uniqueId);
    }
}

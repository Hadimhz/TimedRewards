package dev.hadimhz.rewards.listener;

import dev.hadimhz.rewards.storage.PlayerDataStorage;
import dev.hadimhz.rewards.wrapper.PlayerData;
import gg.supervisor.core.annotation.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Component
public class PlayerListener implements Listener {

    private final PlayerDataStorage playerDataStorage;

    public PlayerListener(PlayerDataStorage playerDataStorage) {
        this.playerDataStorage = playerDataStorage;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = playerDataStorage.load(player.getUniqueId());

        playerData.setName(player.getName());
        playerData.setUuid(player.getUniqueId());
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        playerDataStorage.save(player.getUniqueId());
        playerDataStorage.getRegistry().remove(player.getUniqueId());

    }

}

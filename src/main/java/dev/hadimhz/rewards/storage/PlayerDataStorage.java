package dev.hadimhz.rewards.storage;

import dev.hadimhz.rewards.wrapper.PlayerData;
import gg.supervisor.configuration.json.JsonConfigService;
import gg.supervisor.core.annotation.Component;
import gg.supervisor.util.storage.AbstractStorageRegistry;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

@Component
public class PlayerDataStorage extends AbstractStorageRegistry<UUID, PlayerData> {

    public PlayerDataStorage(Plugin plugin) {
        super(plugin,
                JsonConfigService.class,
                PlayerData.class,
                "players",
                unused -> new PlayerData(),
                UUID::fromString,
                UUID::toString);
    }

}

package dev.hadimhz.rewards;

import co.aikar.commands.PaperCommandManager;
import dev.hadimhz.rewards.storage.PlayerDataStorage;
import dev.hadimhz.rewards.wrapper.PlayerData;
import gg.supervisor.core.loader.SupervisorLoader;
import gg.supervisor.core.util.Services;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TimedRewardsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        final PaperCommandManager commandManager = new PaperCommandManager(this);

        commandManager.enableUnstableAPI("help");

        Services.register(Plugin.class, this);

        SupervisorLoader.register(this, commandManager);

        final PlayerDataStorage playerDataStorage = Services.loadIfPresent(PlayerDataStorage.class);

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = playerDataStorage.load(player.getUniqueId());

            playerData.setName(player.getName());
            playerData.setUuid(player.getUniqueId());
        }

    }

    @Override
    public void onDisable() {
        Services.loadIfPresent(PlayerDataStorage.class).saveAll(false);
    }
}

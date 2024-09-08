package dev.hadimhz.rewards.hook;

import dev.hadimhz.rewards.service.RewardsService;
import dev.hadimhz.rewards.storage.PlayerDataStorage;
import dev.hadimhz.rewards.storage.TimeRewardStorage;
import dev.hadimhz.rewards.wrapper.PlayerData;
import dev.hadimhz.rewards.wrapper.TimedReward;
import gg.supervisor.core.annotation.Component;
import gg.supervisor.util.chat.TimeUtil;
import lombok.AllArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


@Component
public class PlaceHolderApiHook extends PlaceholderExpansion {

    private final RewardsService rewardsService;
    private final PlayerDataStorage playerDataStorage;
    private final TimeRewardStorage timeRewardStorage;

    public PlaceHolderApiHook(Plugin plugin, RewardsService rewardsService, PlayerDataStorage playerDataStorage, TimeRewardStorage timeRewardStorage) {
        this.rewardsService = rewardsService;
        this.playerDataStorage = playerDataStorage;
        this.timeRewardStorage = timeRewardStorage;

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) return;

        plugin.getLogger().info("Detected PAPI! Creating placeholders.");
        register();

    }

    @Override
    public @NotNull String getIdentifier() {
        return "timedrewards";
    }

    @Override
    public @NotNull String getAuthor() {
        return "TimedRewards";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        String[] args = params.split(":");

        if (args.length < 2) {
            return "You must specify a timedRewardId like this: %timedrewards_cooldown:daily%";
        }

        final PlayerData playerData = this.playerDataStorage.get(player.getUniqueId());
        final TimedReward timedReward = this.timeRewardStorage.get(args[1]);

        if (timedReward == null) {
            return "invalid timedRewardId: " + args[1];
        }

        switch (args[0].toLowerCase()) {
            case "display" -> {
                return timedReward.getDisplayName();
            }
            case "cooldown" -> {
                return TimeUtil.toString(rewardsService.getRemainingTime(playerData, timedReward), false);
            }
            case "cooldown_short" -> {
                return TimeUtil.toString(rewardsService.getRemainingTime(playerData, timedReward));
            }
            case "duration" -> {
                return TimeUtil.toString(timedReward.getClaimEveryXSeconds(), false);
            }
            case "duration_short" -> {
                return TimeUtil.toString(timedReward.getClaimEveryXSeconds());
            }
            case "available" -> {
                return String.valueOf(!rewardsService.isOnCooldown(playerData, timedReward));
            }
        }

        return null;
    }

}

package dev.hadimhz.rewards.service;

import dev.hadimhz.rewards.Config.RewardsConfig;
import dev.hadimhz.rewards.wrapper.PlayerData;
import dev.hadimhz.rewards.wrapper.TimedReward;
import dev.hadimhz.rewards.wrapper.WeightedEntry;
import gg.supervisor.core.annotation.Component;
import gg.supervisor.core.util.Services;
import gg.supervisor.util.chat.Text;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Component
@AllArgsConstructor
public class RewardsService {

    private final RewardsConfig config;

    public long getRemainingTime(PlayerData playerData, TimedReward timedReward) {
        long lastClaimed = playerData.getLastClaimed().getOrDefault(timedReward.getId(), 0L);

        return Math.floorDiv(lastClaimed - System.currentTimeMillis(), 1000) + timedReward.getClaimEveryXSeconds();
    }

    public boolean isOnCooldown(PlayerData playerData, TimedReward timedReward) {
        return getRemainingTime(playerData, timedReward) > 0;
    }

    public void giveRewards(Player player, TimedReward timedReward) {
        List<WeightedEntry> rewards = timedReward.getWeightedRandomSelector().select(timedReward.getGiveXRewards(), false, WeightedEntry::getWeight);

        for (WeightedEntry reward : rewards) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward.getCommand().replace("%p%", player.getName()));
            player.sendMessage(Text.translate(config.getRewardMessage()
                    .replace("%reward%", reward.getDisplayName())
                    .replace("%name%", timedReward.getDisplayName())));

        }

        Services.getService(Plugin.class).getLogger().info(player.getName() + " has claimed " + timedReward.getDisplayName() + "(" + timedReward.getId() + ")");
    }

}

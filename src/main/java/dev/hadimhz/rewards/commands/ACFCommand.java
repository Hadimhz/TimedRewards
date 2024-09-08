package dev.hadimhz.rewards.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import dev.hadimhz.rewards.Config.RewardsConfig;
import dev.hadimhz.rewards.service.RewardsService;
import dev.hadimhz.rewards.storage.PlayerDataStorage;
import dev.hadimhz.rewards.storage.TimeRewardStorage;
import dev.hadimhz.rewards.wrapper.PlayerData;
import dev.hadimhz.rewards.wrapper.TimedReward;
import gg.supervisor.core.annotation.Component;
import gg.supervisor.util.chat.Text;
import gg.supervisor.util.chat.TimeUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Component
@CommandAlias("timeRewards")
public class ACFCommand extends BaseCommand {

    private final RewardsConfig config;

    private final RewardsService rewardsService;
    private final PlayerDataStorage playerDataStorage;
    private final TimeRewardStorage timeRewardStorage;

    public ACFCommand(PaperCommandManager commandManager, RewardsConfig config, RewardsService rewardsService, PlayerDataStorage playerDataStorage, TimeRewardStorage timeRewardStorage) {
        this.config = config;
        this.rewardsService = rewardsService;
        this.playerDataStorage = playerDataStorage;
        this.timeRewardStorage = timeRewardStorage;

        commandManager.getCommandCompletions().registerCompletion("timed_reward", c ->
                timeRewardStorage.getRegistry().values().stream().filter(x -> c.getSender().hasPermission(x.getPermission())).map(TimedReward::getId).toList());

        commandManager.registerCommand(this);
    }

    @Default
    @HelpCommand
    public void onDefault(CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("list")
    @CommandPermission("timerewards.admin")
    public void onList(CommandSender sender) {
        sender.sendMessage(Text.translate("&fName &7(&oID&7) &8- &fPermission &8- &fCooldown"));

        for (TimedReward timedReward : timeRewardStorage.getRegistry().values()) {
            sender.sendMessage(Text.translate("&f" + timedReward.getDisplayName() + "&7(&o" + timedReward.getId()
                    + "&7) &8- &f" + timedReward.getPermission()
                    + " &8- &f" + TimeUtil.toString(timedReward.getClaimEveryXSeconds())
            ));
        }

    }

    @Subcommand("claimFor")
    @CommandCompletion("@players @timed_reward")
    @Syntax("<player> <rewardPool>")
    @CommandPermission("timerewards.admin")
    public void onClaimFor(CommandSender sender, OnlinePlayer onlinePlayer, String timedRewardId) {

        final Player target = onlinePlayer.getPlayer();

        final PlayerData playerData = this.playerDataStorage.get(target.getUniqueId());
        final TimedReward timedReward = this.timeRewardStorage.get(timedRewardId);

        if (timedReward == null) {
            sender.sendMessage(Text.translate("&cTimeReward does not exist"));
            return;
        }

        if (rewardsService.isOnCooldown(playerData, timedReward)) {
            sender.sendMessage(Text.translate("&cThis time reward is still on cooldown for this player!"));
            target.sendMessage(Text.translate(config.getCooldownMessage()
                    .replace("%remaining%", TimeUtil.toString(rewardsService.getRemainingTime(playerData, timedReward), false))
            ));
            return;
        }

        playerData.getLastClaimed().put(timedRewardId, System.currentTimeMillis());

        rewardsService.giveRewards(target, timedReward);

    }

    @Subcommand("resetCooldown")
    @CommandCompletion("@players @timed_reward")
    @Syntax("<player> <rewardPool>")
    @CommandPermission("timerewards.admin")
    public void onResetCooldown(CommandSender sender, OnlinePlayer onlinePlayer, String timedRewardId) {

        final Player target = onlinePlayer.getPlayer();

        final TimedReward timedReward = this.timeRewardStorage.get(timedRewardId);

        if (timedReward == null) {
            sender.sendMessage(Text.translate("&cTimeReward does not exist"));
            return;
        }

        final PlayerData playerData = this.playerDataStorage.get(target.getUniqueId());

        playerData.getLastClaimed().remove(timedRewardId);

        sender.sendMessage(Text.translate("&aTimeReward cooldown has been reset!"));
    }

    @Subcommand("claim")
    @CommandCompletion("@timed_reward")
    @Syntax("<rewardPool>")
    public void onClaim(Player player, String timedRewardId) {


        final TimedReward timedReward = this.timeRewardStorage.get(timedRewardId);
        if (timedReward == null) {
            player.sendMessage(Text.translate("&cTimeReward does not exist"));
            return;
        }

        if (player.hasPermission(timedReward.getPermission())) {
            player.sendMessage(Text.translate(config.getNoPermissionMessage()));
            return;
        }

        final PlayerData playerData = this.playerDataStorage.get(player.getUniqueId());

        if (rewardsService.isOnCooldown(playerData, timedReward)) {
            player.sendMessage(Text.translate(config.getCooldownMessage()
                    .replace("%remaining%", TimeUtil.toString(rewardsService.getRemainingTime(playerData, timedReward), false))
            ));
            return;
        }

        playerData.getLastClaimed().put(timedRewardId, System.currentTimeMillis());

        rewardsService.giveRewards(player, timedReward);
    }
}

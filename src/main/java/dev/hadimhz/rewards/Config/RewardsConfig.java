package dev.hadimhz.rewards.Config;

import gg.supervisor.configuration.yaml.YamlConfigService;
import gg.supervisor.core.annotation.Configuration;
import lombok.Getter;

@Getter
@Configuration(service = YamlConfigService.class, fileName = "config.yml")
@SuppressWarnings("FieldMayBeFinal")
public class RewardsConfig {

    private String rewardMessage = "You have received %reward% reward from %name%";
    private String cooldownMessage = "This rewards is still on cooldown for %remaining%";
    private String noPermissionMessage = "You do not have the permission required to claim this reward.";

}

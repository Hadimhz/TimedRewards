package dev.hadimhz.rewards.storage;

import dev.hadimhz.rewards.wrapper.TimedReward;
import gg.supervisor.configuration.json.JsonConfigService;
import gg.supervisor.configuration.yaml.YamlConfigService;
import gg.supervisor.core.annotation.Component;
import gg.supervisor.util.storage.AbstractStorageRegistry;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Objects;

@Component
public class TimeRewardStorage extends AbstractStorageRegistry<String, TimedReward> {

    public TimeRewardStorage(Plugin plugin) {
        super(plugin,
                YamlConfigService.class,
                TimedReward.class,
                "rewards",
                unused -> new TimedReward(),
                str -> str,
                str -> str);


        if (Objects.requireNonNull(folder.listFiles()).length == 0) {
            load("daily");
            return;
        }

        for (File file : folder.listFiles()) {

            final TimedReward timedReward = load(file);
            timedReward.setId(file.getName().replace(configService.getExtension(), ""));

            plugin.getLogger().info("Loaded TimedReward: " + timedReward.getId());
        }


    }

}

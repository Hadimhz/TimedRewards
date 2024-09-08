package dev.hadimhz.rewards.wrapper;

import gg.supervisor.util.selector.WeightedRandomSelector;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@SuppressWarnings("FieldMayBeFinal")
public class TimedReward {

    private String displayName = "Daily Reward";
    private long claimEveryXSeconds = 86400;
    private String permission = "timedrewards.daily";

    private int giveXRewards = 1;

    private List<WeightedEntry> entries = List.of(new WeightedEntry("1x Diamond", "give %p% diamond 1", 1), new WeightedEntry("1x Gold Ingot", "give %p% gold_ingot 1", 1));

    private transient WeightedRandomSelector<WeightedEntry> weightedRandomSelector = new WeightedRandomSelector<>(entries);

    private transient @Setter String id;

    public WeightedRandomSelector<WeightedEntry> getWeightedRandomSelector() {
        if (this.weightedRandomSelector == null)
            this.weightedRandomSelector = new WeightedRandomSelector<>(entries);

        return weightedRandomSelector;
    }
}

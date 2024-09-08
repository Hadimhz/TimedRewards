package dev.hadimhz.rewards.wrapper;

import gg.supervisor.util.selector.Weighted;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeightedEntry implements Weighted {

    private String displayName;
    private String command;
    private double weight;

}

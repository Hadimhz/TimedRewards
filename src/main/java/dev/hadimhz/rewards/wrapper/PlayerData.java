package dev.hadimhz.rewards.wrapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PlayerData {

    private @Setter UUID uuid;

    private @Setter String name;

    private Map<String, Long> lastClaimed = new HashMap<>();

}

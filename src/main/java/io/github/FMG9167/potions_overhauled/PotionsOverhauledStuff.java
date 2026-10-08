package io.github.FMG9167.potions_overhauled;

import java.util.HashMap;
import java.util.Map;

public class PotionsOverhauledStuff {
    public static final Map<String, Map<Integer, String>> EffectMap = new HashMap<>();

    static {
        EffectMap.put("vitality",  new HashMap<>() {{ put(0, "Regeneration");put(1, "Health Boost");put(2, "Nausea"); }} );
    }
}

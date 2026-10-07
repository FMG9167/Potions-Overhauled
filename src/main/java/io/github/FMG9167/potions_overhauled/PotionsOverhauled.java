package io.github.FMG9167.potions_overhauled;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotionsOverhauled implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("PotionsOverhauled");
    public static final String MOD_ID = "potions_overhauled";

    @Override
    public void onInitialize() {
        PotionsOverhauledItems.onInitialize();
        PotionsOverhauledComponents.initialize();
    }
}

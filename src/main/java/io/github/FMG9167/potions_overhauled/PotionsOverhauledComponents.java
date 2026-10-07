package io.github.FMG9167.potions_overhauled;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PotionsOverhauledComponents {

    public static final ComponentType<BrewData> BREW_DATA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(PotionsOverhauled.MOD_ID, "brew_data"),
            ComponentType.<BrewData>builder().codec(BrewData.CODEC).build()
    );

    protected static void initialize() {}
}

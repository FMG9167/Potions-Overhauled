package io.github.FMG9167.potions_overhauled;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class PotionsOverhauledItems {
    public static final RegistryKey<ItemGroup> POTIONS_OVERHAULED_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(PotionsOverhauled.MOD_ID, "item_group"));
    public static final ItemGroup POTIONS_OVERHAULED_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.POTION))
            .displayName(Text.translatable("itemGroup.potionsOverhauled"))
            .build();

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(PotionsOverhauled.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static Item register(String id) {
        return register(new Item(new Item.Settings()) {
            @Override
            public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
                tooltip.add(Text.translatable("item.potionsOverhauled."+id+".tooltip"));
            }
        }, id);
    }

    public static final Item SLUDGE = register("sludge");

    public static void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, POTIONS_OVERHAULED_GROUP_KEY, POTIONS_OVERHAULED_GROUP);

        ItemGroupEvents.modifyEntriesEvent(POTIONS_OVERHAULED_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(SLUDGE);
        });
    }
}

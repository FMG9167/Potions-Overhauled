package io.github.FMG9167.potions_overhauled.mixin;

import io.github.FMG9167.potions_overhauled.BrewData;
import io.github.FMG9167.potions_overhauled.PotionsOverhauledComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(PotionItem.class)
public abstract class TooltipForPotionMixin {

    @Inject(method = "appendTooltip", at = @At("HEAD"), cancellable = true)
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {

        if(!Objects.equals(stack.getOrDefault(PotionsOverhauledComponents.BREW_DATA, BrewData.EMPTY), BrewData.EMPTY)) {
            String dominantTrait = BrewData.dominantTrait(Objects.requireNonNull(stack.get(PotionsOverhauledComponents.BREW_DATA)));

            if(!Objects.equals(dominantTrait, "")) {
                tooltip.add(Text.literal( dominantTrait + " (3:00)").formatted(Formatting.BLUE));
                String subTrait = BrewData.subTrait(Objects.requireNonNull(stack.get(PotionsOverhauledComponents.BREW_DATA)), dominantTrait);
                if(!Objects.equals(subTrait, "")) {
                    tooltip.add(Text.literal(subTrait + " (1:00)").formatted(Formatting.BLUE));
                }
                ci.cancel();
            }

        }

    }
}

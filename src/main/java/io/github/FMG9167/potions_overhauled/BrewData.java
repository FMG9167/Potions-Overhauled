package io.github.FMG9167.potions_overhauled;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.MathHelper;

public record BrewData(
        int vitality, int speed, int strength, int toughness, int light, int water, int hunger, int stability, int toxicity, int ingredientCount
) {
    public static final int AXIS_LIMIT = 20;
    public static final int MAX_INGREDIENTS = 5;

    public static final BrewData EMPTY = new BrewData(0,0,0,0,0,0,0,0,0,0);

    private static final Codec<Integer> AXIS = Codec.intRange(-AXIS_LIMIT, AXIS_LIMIT);
    private static final Codec<Integer> NON_NEGATIVE =  Codec.intRange(0, AXIS_LIMIT);

    public static final Codec<BrewData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            AXIS.optionalFieldOf("vitality", 0).forGetter(BrewData::vitality),
            AXIS.optionalFieldOf("speed", 0).forGetter(BrewData::speed),
            AXIS.optionalFieldOf("strength", 0).forGetter(BrewData::strength),
            AXIS.optionalFieldOf("toughness", 0).forGetter(BrewData::toughness),
            AXIS.optionalFieldOf("light", 0).forGetter(BrewData::light),
            AXIS.optionalFieldOf("water", 0).forGetter(BrewData::water),
            AXIS.optionalFieldOf("hunger", 0).forGetter(BrewData::hunger),
            AXIS.optionalFieldOf("stability", 0).forGetter(BrewData::stability),
            NON_NEGATIVE.optionalFieldOf("toxicity", 0).forGetter(BrewData::toxicity),
            NON_NEGATIVE.optionalFieldOf("ingredientCount", 0).forGetter(BrewData::ingredientCount)
    ).apply(instance, BrewData::new));

    public boolean canAddIngredients() {
        return ingredientCount < MAX_INGREDIENTS;
    }

    public BrewData withIngredient(BrewData contribution) {
        return new BrewData(
                clampAxis(vitality + contribution.vitality),
                clampAxis(speed + contribution.speed),
                clampAxis(strength + contribution.strength),
                clampAxis(toughness + contribution.toughness),
                clampAxis(light + contribution.light),
                clampAxis(water + contribution.water),
                clampAxis(hunger + contribution.hunger),
                clampAxis(stability + contribution.stability),
                Math.max(0, toxicity + contribution.toxicity),
                ingredientCount + 1
        );
    }

    private static int clampAxis(int value) {
        return MathHelper.clamp(value, -AXIS_LIMIT, AXIS_LIMIT);
    }
}

package io.github.FMG9167.potions_overhauled;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.MathHelper;

public record BrewData(
        int vitality, int speed, int strength, int toughness, int light, int water, int hunger, int stability, int potency, int duration, int toxicity, int ingredientCount
) {
    public static final int AXIS_LIMIT = 20;
    public static final int MAX_INGREDIENTS = 5;

    public static final BrewData EMPTY = new BrewData(0,0,0,0,0,0,0,0,0,0, 0, 0);

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
            NON_NEGATIVE.optionalFieldOf("potency", 0).forGetter(BrewData::potency),
            NON_NEGATIVE.optionalFieldOf("duration", 0).forGetter(BrewData::duration),
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
                Math.max(0, potency + contribution.potency),
                Math.max(0, duration + contribution.duration),
                Math.max(0, toxicity + contribution.toxicity),
                ingredientCount + 1
        );
    }

    public static String subTrait(BrewData brewData, String dominantTrait) {
        int maxId = idFromTrait(dominantTrait);
        int max = -20;
        int id = -1;
        int[] effects = {
                brewData.vitality,
                brewData.speed,
                brewData.strength,
                brewData.toughness,
                brewData.light,
                brewData.water,
                brewData.hunger
        };

        for(int i = 0; i < effects.length; i++) {
            if(i == maxId) {
                continue;
            }
            if(effects[i] > max) {
                max = effects[i];
                id = i;
            }
        }

        return traitFromId(id);

    }

    public static String dominantTrait(BrewData brewData) {
        int max = brewData.vitality;
        int id = 0;
        int[] effects = {
                brewData.vitality,
                brewData.speed,
                brewData.strength,
                brewData.toughness,
                brewData.light,
                brewData.water,
                brewData.hunger
        };

        for(int i = 1; i < effects.length; i++) {
            if(effects[i] > max) {
                max = effects[i];
                id = i;
            }
        }

        effects[id] = -25;
        int id2 = -1;
        for(int i = 0; i < effects.length; i++) {
            if(effects[i] == max){
                id2 = i;
            }
        }

        if(max == 0) {
            return "";
        }

        if(id2 == -1) {
            return traitFromId(id);
        } else {
            return hybridTrait(traitFromId(id), traitFromId(id2));
        }
    }

    private static int clampAxis(int value) {
        return MathHelper.clamp(value, -AXIS_LIMIT, AXIS_LIMIT);
    }

    private static String hybridTrait(String trait1, String trait2) {
        return trait1 + " " + trait2;
    }

    private static String traitFromId(int id) {
        return switch (id) {
            case 0 -> "Vitality";
            case 1 -> "Speed";
            case 2 -> "Strength";
            case 3 -> "Toughness";
            case 4 -> "Light";
            case 5 -> "Water";
            case 6 -> "Hunger";
            default -> "";
        };
    }

    private static int idFromTrait(String trait) {
        return switch(trait) {
            case "Vitality" -> 0;
            case "Speed" -> 1;
            case "Strength" -> 2;
            case "Toughness" -> 3;
            case "Light" -> 4;
            case "Water" -> 5;
            case "Hunger" -> 6;
            default -> -1;
        };
    }
}

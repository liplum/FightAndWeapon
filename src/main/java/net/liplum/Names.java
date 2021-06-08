package net.liplum;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public final class Names {
    public static String prefixRegister(String name) {
        return String.format("%s:%s", MetaData.MOD_ID, name);
    }

    public static <T extends IForgeRegistryEntry.Impl<T>> T setRegisterName(T namedObject, String registerName) {
        return namedObject.setRegistryName(MetaData.MOD_ID, registerName);
    }

    public static ResourceLocation genResourceLocation(String resourcePath) {
        return new ResourceLocation(MetaData.MOD_ID, resourcePath);
    }

    public static class Item {
        public static final String MaterialQuartz = "material_quartz";
        public static final String QuartzSwordItem = "quartz_sword";
        public static final String QuartzAxeItem = "quartz_axe";
        public static final String TestSwordItem = "test_sword";
        public static final String BattleAxeItem = "battle_axe";

        public static class Lance {
            public static final String ArenaLanceItem = "arena_lance";
            public static final String TrainingLanceItem = "training_lance";
            public static final String LightLanceItem = "light_lance";
            public static final String KnightLanceItem = "knight_lance";
        }

        public static class Harp {
            public static final String HarpItem = "harp";
        }
    }

    public final static class Entity {
        public static final String StraightDamageEntity = "straight_damage";
    }

    public final static class Capability {
        public static final String Master = "master";
    }

    public final static class ItemGroup {
        public static final String FawWeapons = "faw_weapons";
        public static final String FawGemstones = "faw_gemstones";
        public static final String FawForges = "faw_forges";
    }

    public final static class Category {
        public static final String Unknown = "unknown";
        public static final String Melee = "melee";
        public static final String LongRange = "long_range";
        public static final String Magic = "magic";
        public static final String AOE = "aoe";
        public static final String Single = "single";
        public static final String Buffier = "buffier";
    }

    public final static class Potion {
        public static final String UnstoppablePotion = "unstoppable";
    }

    public final static class Command{
        public static final String Inlay = "inlay";
    }

    public final static class Gemstone {
        public static final String Ruby = "ruby";
        public static final String Aquamarine = "aquamarine";
        public static final String Citrine = "citrine";
        public static final String Jadeite = "jadeite";
        public static final String Amethyst = "amethyst";
        public static final String RoseQuartz = "rose_quartz";
        public static final String Turquoise = "turquoise";

        public static final String Flamegem = "flamegem";
        public static final String Marinegem = "marinegem";
        public static final String Earthgem = "earthgem";
        public static final String Forestgem = "forestgem";
        public static final String Endergem = "endergem";
        public static final String MagicPearl = "magic_pearl";
        public static final String WindyGemstone = "windy_gemstone";
    }

    public final static class PassiveSkill {
        public static final String FireProof = "FireProof";
        public static final String FireResistance = "FireResistance";


    }
}
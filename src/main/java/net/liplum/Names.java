package net.liplum;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public final class Names {
    public static String prefixRegister(String name) {
        return MetaData.MOD_ID + ':' + name;
    }

    public static <T extends IForgeRegistryEntry.Impl<T>> T setRegisterName(T namedObject, String registerName) {
        return namedObject.setRegistryName(MetaData.MOD_ID, registerName);
    }

    public static ResourceLocation genResourceLocation(String resourcePath) {
        return new ResourceLocation(MetaData.MOD_ID, resourcePath);
    }

    public static final class Special {
        public static final String Weapon = "weapon";
        public static final String Master = "master";
        public static final String PassiveSkill = "pskill";
        public static final String Generic = "generic";
        public static final String Attributes = "attributes";
    }

    public static final class Item {
        public static final String MaterialQuartz = "material_quartz";
        public static final String QuartzSwordItem = "quartz_sword";
        public static final String QuartzAxeItem = "quartz_axe";
        public static final String TestSwordItem = "test_sword";
        public static final String BattleAxeItem = "battle_axe";

        public static final String InlayingToolItem = "inlaying_tool";
        public static final String BlacksmithGloveItem = "blacksmith_glove";
        public static final String ForgeHammerItem = "forge_hammer";

        public static final class Lance {
            public static final String TypeName = "lance";
            public static final String ArenaLanceItem = "arena_lance";
            public static final String TrainingLanceItem = "training_lance";
            public static final String LightLanceItem = "light_lance";
            public static final String KnightLanceItem = "knight_lance";
        }

        public static final class Harp {
            public static final String TypeName = "harp";
            public static final String HarpItem = "harp";
        }
    }

    public static final class Attribute {

        public static final class Generic {
            public static final String Strength = "Strength";
            public static final String CoolDown = "CoolDown";
            public static final String AbilityPower = "AbilityPower";
        }

        public static final class Lance {
            public static final String SprintLength = "SprintLength";
        }

        public static final class Harp {
            public static final String Radius = "Radius";
            public static final String Frequency = "Frequency";
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

    public final static class Command {
        public static final String Inlay = "inlay";

        public final static class InlaySub {
            public static final String Remove = "remove";
        }
    }

    public final static class Block {
        public static final String InlayTable = "inlay_table";
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

        public static final String UnstoppableSprint = "UnstoppableSprint";

        public static final String Fireproof = "Fireproof";
        public static final String Magicize = "Magicize";
        public static final String MagicAttach = "MagicAttach";
        public static final String GentlyLand = "GentlyLand";
        public static final String Feather = "Feather";
        public static final String FireResistance = "FireResistance";
        public static final String Levitation = "Levitation";
        public static final String NutrientAbsorption = "NutrientAbsorption";
    }

    public final static class Master {
    }
}
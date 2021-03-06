package net.liplum;

import net.liplum.api.annotations.LongSupport;
import net.minecraftforge.registries.IForgeRegistryEntry;

@LongSupport
public final class Names {
    public static final String WeaponAttributeModifier = "Weapon modifier";

    public static String prefixRegister(String name) {
        return MetaData.MOD_ID + ':' + name;
    }

    public static <T extends IForgeRegistryEntry.Impl<T>> T setRegisterName(T namedObject, String registerName) {
        return namedObject.setRegistryName(MetaData.MOD_ID, registerName);
    }

    public static final class Special {
        public static final String Weapon = "weapon";
        public static final String Mastery = "mastery";
        public static final String PassiveSkill = "pskill";
        public static final String Generic = "generic";
        public static final String Attributes = "attributes";
        public static final String Blueprint = "blueprint";
        public static final String Parts = "parts";
        public static final String Unit = "unit";
        public static final String WeaponType = "weaponType";
        public static final String WeaponSkill = "skill";
    }

    public static final class Item {
        public static final String MaterialQuartz = "material_quartz";
        public static final String QuartzSwordItem = "quartz_sword";
        public static final String QuartzAxeItem = "quartz_axe";
        public static final String TestSwordItem = "test_sword";

        public static final String InlayingToolItem = "inlaying_tool";
        public static final String BlacksmithGloveItem = "blacksmith_glove";
        public static final String ForgeHammerItem = "forge_hammer";
        public static final String CastBlueprintItem = "cast_blueprint";
        public static final String WeaponPartItem = "weapon_part";

        public static final String EmptyCore = "default";

        public static final class Lance {
            public static final String TypeName = "lance";
            public static final String ArenaLanceItem = "arena_lance";
            public static final String TrainingLanceItem = "training_lance";
            public static final String LightLanceItem = "light_lance";
            public static final String KnightLanceItem = "knight_lance";
            public static final String DrillLanceItem = "drill_lance";
            public static final String DrillLanceItem_Property_Drilling = "drilling";
        }

        public static final class Harp {
            public static final String TypeName = "harp";
            public static final String HarpItem = "harp";
        }

        public static final class BattleAxe {
            public static final String TypeName = "battle_axe";
            public static final String BattleAxeItem = "battle_axe";
            public static final String BerserkerAxeItem = "berserker_axe";
        }

        public static final class Sword {
            public static final String TypeName = "sword";
        }

        public static final class Bow {
            public static final String TypeName = "bow";
            public static final String SimpleBow = "simple_bow";
        }

        public static final class MagickWand {
            public static final String TypeName = "magick_wand";
            public static final String GemswordItem = "gemsword";
            public static final String MagickSwordItem = "magick_sword";
            public static final String AthameItem = "athame";
        }

        public static final class Ranged {
            public static final String TypeName = "ranged";
            public static final String SickleItem = "sickle";
            public static final String ChainedHammerItem = "chained_hammer";
            public static final String MeteorHammerItem = "meteor_hammer";
        }
    }

    public static final class Attribute {

        public static final class Generic {
            public static final String Durability = "Durability";
            public static final String Strength = "Strength";
            public static final String CoolDown = "CoolDown";
            public static final String AttackReach = "AttackReach";
            public static final String AttackSpeed = "AttackSpeed";
            public static final String AbilityPower = "AbilityPower";
            public static final String EnemyBreakingTime = "EnemyBreakingTime";
            public static final String KnockbackStrength = "KnockbackStrength";
            public static final String MaxUseDuration = "MaxUseDuration";
            public static final String DropsFireproof = "DropsFireproof";
            public static final String SpecialAttackReachJudgment = "SpecialAttackReachJudgment";
        }

        public static final class Lance {
            public static final String SprintStrength = "SprintStrength";
        }

        public static final class BattleAxe {
            public static final String SweepRange = "SweepRange";
        }

        public static final class Sword {
            public static final String Sweep = "Sweep";
        }

        public static final class Harp {
            public static final String Radius = "Radius";
            public static final String Frequency = "Frequency";
        }

        public static final class Bow {
            public static final String Force = "Force";
            public static final String Velocity = "Velocity";
            public static final String KnockBack = "KnockBack";
        }
    }

    public final static class Entity {
        public static final String StraightDamageEntity = "straight_damage";
        public static final String GemswordBeam = "gemsword_beam";
        public static final String FlyingItem = "flying_item";
    }

    public final static class Capability {
        public static final String Mastery = "mastery";
        public static final String CastStudy = "cast_study";
        public static final String Timer = "timer";
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
        public static final String Mastery = "mastery";
        public static final String ClearCD = "clearcd";

        public final static class ClearCDSub {
            public static final String All = "all";
        }

        public final static class MasterySub {
            public static final String MasteryShow = "show";
            public static final String MasteryExp = "exp";
            public static final String MasteryReset = "reset";
            public static final String All = "all";
        }

        public final static class InlaySub {
            public static final String Remove = "remove";
        }
    }

    public final static class Block {
        public static final String InlayTable = "inlay_table";
        public static final String Forge = "forge";
    }

    public final static class Gemstone {
        public static final String RubyN = "ruby";
        public static final String AquamarineN = "aquamarine";
        public static final String CitrineN = "citrine";
        public static final String JadeiteN = "jadeite";
        public static final String AmethystN = "amethyst";
        public static final String RoseQuartzN = "rose_quartz";
        public static final String TurquoiseN = "turquoise";

        public static final String FlamegemN = "flamegem";
        public static final String MarinegemN = "marinegem";
        public static final String EarthgemN = "earthgem";
        public static final String ForestgemN = "forestgem";
        public static final String EndergemN = "endergem";
        public static final String MagicPearlN = "magic_pearl";
        public static final String WindyGemstoneN = "windy_gemstone";
    }

    public final static class PassiveSkill {

        public static final String Unstoppable = "Unstoppable";

        public static final String Fireproof = "Fireproof";
        public static final String ScorchingTouch = "ScorchingTouch";
        public static final String Magicize = "Magicize";
        public static final String MagicAttach = "MagicAttach";
        public static final String GentlyLand = "GentlyLand";
        public static final String Feather = "Feather";
        public static final String FireResistance = "FireResistance";
        public static final String Levitation = "Levitation";
        public static final String NutrientAbsorption = "NutrientAbsorption";
        public static final String ExpMending = "ExpMending";
        public static final String FrostWalker = "FrostWalker";
        public static final String Sweeping = "Sweeping";
        public static final String AP2Strength = "AP2Strength";
        public static final String Combo = "Combo";
        public static final String ManaBarrier = "ManaBarrier";

        public final static class WeaponCore {
            public static final String RangedWeaponPS = "RangedWeaponPS";
        }
    }

    public final static class Mastery {
    }

    public final static class Behavior {
        public static final String KillEntity = "KillEntity";
        public static final String HealWeapon = "HealWeapon";
        public static final String DamageWeapon = "DamageWeapon";
        public static final String CauseDamage = "CauseDamage";
        public static final String DestroyBlock = "DestroyBlock";
        public static final String ReleaseWeaponSkill = "ReleaseWeaponSkill";
        public static final String UseWeapon = "UseWeapon";
        public static final String TriggerPassiveSkill = "TriggerPassiveSkill";
        public static final String Injury = "Injury";

        public static final String HarpHealing = "HarpHealing";
        public static final String HarpBuff = "HarpBuff";
    }
}
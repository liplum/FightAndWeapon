package net.liplum;

public final class Names {
    public static String prefixRegister(String name) {
        return String.format("%s:%s", MetaData.MOD_ID, name);
    }

    public static String prefixUnloc(String name) {
        return String.format("%s.%s", MetaData.MOD_ID, name);
    }

    public static String prefixPotionUnloc(String name) {
        return String.format("%s.%s.%s", "potion", MetaData.MOD_ID, name);
    }

    public static class Item {
        public static final String RubyItem = "ruby";
        public static final String MaterialQuartz = "material_quartz";
        public static final String QuartzSwordItem = "quartz_sword";
        public static final String QuartzAxeItem = "quartz_axe";
        public static final String TestSwordItem = "test_sword";
        public static final String BattleAxeItem = "battle_axe";

        public static class Lance {
            public static final String LanceItem = "lance";
            public static final String KnightLanceItem = "knight_lance";
        }

        public static class Harp {
            public static final String HarpItem = "harp";
        }
    }

    public final static class Entity {
        public static final String StraightDamageEntity = "straight_damage";
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

    public final static class Gemstone{
        public static final String Ruby = "ruby";
        public static final String Endergem = "endergem";
        public static final String Flamegem = "flamegem";
    }
}

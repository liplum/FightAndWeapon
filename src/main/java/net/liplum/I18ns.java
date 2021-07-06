package net.liplum;

public final class I18ns {
    public static String prefixUnloc(String name) {
        return MetaData.MOD_ID + '.' +
                name;
    }

    public static String prefixPotionUnloc(String name) {
        return Vanilla.I18n.Potion + '.' + Vanilla.I18n.Effect + '.' +
                MetaData.MOD_ID + '.' +
                name;
    }

    public static String prefixItem(String name) {
        return Vanilla.I18n.Item + '.' + MetaData.MOD_ID + '.' +
                name;

    }

    public static String prefixHotkeyUnloc(String name) {
        return Vanilla.I18n.Hotkey + '.' +
                MetaData.MOD_ID + '.' +
                name;
    }

    public static String prefixHotkeyCategoryUnloc(String name) {
        return Vanilla.I18n.Hotkey + '.' + Vanilla.I18n.Category + '.' +
                name;
    }

    public static String prefixCommandUnloc(String name) {
        return Vanilla.I18n.Command + '.' +
                name + '.' +
                Vanilla.I18n.Command_Usage;
    }

    public static String prefixTile(String name) {
        return Vanilla.I18n.Tile + '.' +
                MetaData.MOD_ID + '.' +
                name;
    }

    public static String prefixPSkill(String name) {
        return Names.Special.PassiveSkill + '.' +
                MetaData.MOD_ID + '.' +
                name;
    }

    public static String prefixTooltip(String category, String name) {
        return Vanilla.I18n.Tooltip + '.' + MetaData.MOD_ID + '.' + category + '.' +
                name;
    }

    public static String endWithName(String content) {
        return content + '.' +
                Vanilla.I18n.Name;
    }

    public static String endWithDescription(String content) {
        return content + '.' +
                PassiveSkill.Description;
    }

    public static String prefixAttr(String weaponType, String attributeName) {
        return Names.Special.Attributes + '.' + MetaData.MOD_ID + '.' +
                weaponType + '.' + attributeName;
    }

    public static final class PassiveSkill {
        public static final String Description = "description";
    }

    public static final class Hotkey {
        public static final String Faw_Category = prefixHotkeyCategoryUnloc(MetaData.MOD_ID);

        public static final class Master {
            public static final String Master = prefixHotkeyUnloc(Names.Special.Master);
        }
    }

    public static final class Command {
        public static final String Inlay = prefixCommandUnloc(Names.Command.Inlay);
    }

    public static final class Tile {
        public static final String InlayTable_Name = endWithName(prefixTile(Names.Block.InlayTable));
        public static final String Forge_Name = endWithName(prefixTile(Names.Block.Forge));
    }

    public static final class Tooltip {
        public static final String Inlaid = weapon("inlaid");
        public static final String NoGemstone = weapon("noGemstone");
        public static final String NoSuchWeaponPart = weapon("noSuchWeaponPart");

        private static String weapon(String name) {
            return prefixTooltip(Names.Special.Weapon, name);
        }

        private static String unit(String name) {
            return prefixTooltip(Names.Special.Unit, name);
        }

        public static final class Unit {
            public static final String Second = unit("Second");
            public static final String TriggerPerSecond = unit("TriggerPerSecond");
            public static final String Unit = unit("Unit");
        }
    }

    public static final class Attribute {
        public static String Generic(String attributeName) {
            return prefixAttr(Names.Special.Generic, attributeName);
        }

        public static String Lance(String attributeName) {
            return prefixAttr(Names.Item.Lance.TypeName, attributeName);
        }

        public static String Harp(String attributeName) {
            return prefixAttr(Names.Item.Harp.TypeName, attributeName);
        }

        public static String BattleAxe(String attributeName) {
            return prefixAttr(Names.Item.BattleAxe.TypeName, attributeName);
        }
    }
}

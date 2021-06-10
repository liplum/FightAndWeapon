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
        return Names.PassiveSkill.PassiveSkill + '.' +
                MetaData.MOD_ID + '.' +
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

    public static final class PassiveSkill {
        public static final String Description = "description";
    }

    public static final class Hotkey {
        public static final String Faw_Category = prefixHotkeyCategoryUnloc(MetaData.MOD_ID);

        public static final class Master {
            public static final String Master = prefixHotkeyUnloc(Names.Master.Master);
        }
    }

    public static final class Command {
        public static final String Inlay = prefixCommandUnloc(Names.Command.Inlay);
    }

    public static final class Tile {
        public static final String InlayTable_Name = endWithName(prefixTile(Names.Block.InlayTable));
    }
}

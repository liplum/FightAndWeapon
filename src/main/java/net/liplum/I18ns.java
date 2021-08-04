package net.liplum;

import net.liplum.api.annotations.LongSupport;

import javax.annotation.Nonnull;

@LongSupport
public final class I18ns {
    @LongSupport
    public static String prefixUnloc(String name) {
        return MetaData.MOD_ID + '.' +
                name;
    }

    @LongSupport
    public static String prefixPotionUnloc(String name) {
        return Vanilla.I18n.Potion + '.' + Vanilla.I18n.Effect + '.' +
                MetaData.MOD_ID + '.' +
                name;
    }

    @LongSupport
    public static String prefixItem(String name) {
        return Vanilla.I18n.Item + '.' + MetaData.MOD_ID + '.' +
                name;

    }

    @LongSupport
    public static String prefixHotkeyUnloc(String name) {
        return Vanilla.I18n.Hotkey + '.' +
                MetaData.MOD_ID + '.' +
                name;
    }

    @LongSupport
    public static String prefixHotkeyCategoryUnloc(String name) {
        return Vanilla.I18n.Hotkey + '.' + Vanilla.I18n.Category + '.' +
                name;
    }

    @LongSupport
    public static String prefixCommandUsageUnloc(String name) {
        return Vanilla.I18n.Command + '.' +
                name + '.' +
                Vanilla.I18n.Command_Usage;
    }

    @LongSupport
    public static String prefixTile(String name) {
        return Vanilla.I18n.Tile + '.' +
                MetaData.MOD_ID + '.' +
                name;
    }

    @LongSupport
    public static String prefixPSkill(String name) {
        return Names.Special.PassiveSkill + '.' +
                MetaData.MOD_ID + '.' +
                name;
    }

    @LongSupport
    public static String prefixTooltip(String category, String name) {
        return Vanilla.I18n.Tooltip + '.' + MetaData.MOD_ID + '.' + category + '.' +
                name;
    }

    @LongSupport
    public static String endWithName(String content) {
        return content + '.' +
                Vanilla.I18n.Name;
    }

    @LongSupport
    public static String endWithDescription(String content) {
        return content + '.' +
                PassiveSkill.Description;
    }

    @LongSupport
    public static String prefixAttr(String weaponType, String attributeName) {
        return Names.Special.Attributes + '.' + MetaData.MOD_ID + '.' +
                weaponType + '.' + attributeName;
    }

    @LongSupport
    public static String prefixWeaponType(String weaponType) {
        return Names.Special.WeaponType + '.' +
                weaponType;
    }

    @LongSupport
    public static final class PassiveSkill {
        public static final String Description = "description";
    }

    @LongSupport
    public static final class Hotkey {
        public static final String Faw_Category = prefixHotkeyCategoryUnloc(MetaData.MOD_ID);

        @LongSupport
        public static final class Mastery {
            public static final String Mastery = prefixHotkeyUnloc(Names.Special.Mastery);
        }
    }

    @LongSupport
    public static final class Command {
        public static final String ClearCD = prefixCommandUsageUnloc(Names.Command.ClearCD);

        public static final String Inlay = prefixCommandUsageUnloc(Names.Command.Inlay);
        public static final String Inlay_Failure_NotFawWeapon = "commands.inlay.failure.notFawWeapon";
        public static final String Inlay_Failure_NoFawWeapon = "commands.inlay.failure.noGemstone";
        public static final String Inlay_Failure_NoSuchGemstone = "commands.inlay.failure.noSuchGemstone";


        public static final String Mastery = prefixCommandUsageUnloc(Names.Command.Mastery);
        public static final String MasteryExp = prefixCommandUsageUnloc(subCommand(Names.Command.Mastery, Names.Command.MasterySub.MasteryExp));
        public static final String MasteryShow = prefixCommandUsageUnloc(subCommand(Names.Command.Mastery, Names.Command.MasterySub.MasteryShow));
        public static final String MasteryReset = prefixCommandUsageUnloc(subCommand(Names.Command.Mastery, Names.Command.MasterySub.MasteryReset));
        public static final String Mastery_Failure_NotSuchWeaponType = "commands.mastery.failure.noSuchWeaponType";
        public static final String Mastery_Failure_NaN = "commands.mastery.failure.NaN";
        public static final String Mastery_Failure_NegativeNumber = "commands.mastery.failure.negativeNumber";

        public static final String Mastery_Show_Level = "commands.mastery.level";
        public static final String Mastery_Show_Exp = "commands.mastery.exp";

        public static final String Mastery_Yours_Is_Reset = "commands.mastery.yoursIsReset";
        public static final String Mastery_Reset_Successfully = "commands.mastery.resetSuccessfully";
        public static final String Mastery_Reset_Yours_Successfully = "commands.mastery.resetYourSuccessfully";

        public static String subCommand(@Nonnull String parent, @Nonnull String sub) {
            return parent + "." + sub;
        }
    }

    @LongSupport
    public static final class Tile {
        public static final String InlayTable_Name = endWithName(prefixTile(Names.Block.InlayTable));
        public static final String Forge_Name = endWithName(prefixTile(Names.Block.Forge));
    }

    @LongSupport
    public static final class Tooltip {
        public static final String Inlaid = weapon("inlaid");
        public static final String NoGemstone = weapon("noGemstone");
        public static final String NoSuchWeaponPart = weapon("noSuchWeaponPart");
        public static final String Broken = weapon("broken");

        @LongSupport
        private static String weapon(String name) {
            return prefixTooltip(Names.Special.Weapon, name);
        }

        @LongSupport
        private static String unit(String name) {
            return prefixTooltip(Names.Special.Unit, name);
        }

        @LongSupport
        public static String skill(String weaponType, String weaponName) {
            return prefixTooltip(Names.Special.WeaponSkill,
                    weaponType + "." + weaponName);
        }

        @LongSupport
        public static String skill(String weaponType, String weaponName, String gemstone) {
            return prefixTooltip(Names.Special.WeaponSkill,
                    weaponType + "." + weaponName + "." + gemstone);
        }

        @LongSupport
        public static final class Unit {
            public static final String Second = unit("Second");
            public static final String TriggerPerSecond = unit("TriggerPerSecond");
            public static final String Unit = unit("Unit");
        }
    }

    @LongSupport
    public static final class Attribute {
        @LongSupport
        public static String Generic(String attributeName) {
            return prefixAttr(Names.Special.Generic, attributeName);
        }

        @LongSupport
        public static String Lance(String attributeName) {
            return prefixAttr(Names.Item.Lance.TypeName, attributeName);
        }

        @LongSupport
        public static String Harp(String attributeName) {
            return prefixAttr(Names.Item.Harp.TypeName, attributeName);
        }

        @LongSupport
        public static String BattleAxe(String attributeName) {
            return prefixAttr(Names.Item.BattleAxe.TypeName, attributeName);
        }

        @LongSupport
        public static String Sword(String attributeName) {
            return prefixAttr(Names.Item.Sword.TypeName, attributeName);
        }
    }
}

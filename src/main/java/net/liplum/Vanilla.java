package net.liplum;

import net.liplum.lib.OpenItem;

public final class Vanilla {
    public static final int TPS = 20;
    public static final int PlayerMainInventorySlotCount = 36;
    public static final int PlayerArmorInventorySlotCount = 4;
    public static final int PlayerOffHandInventorySlotCount = 1;
    /**
     * Order: Main(36) -> Armor(4) -> Off Hand(1)
     */
    public static final int PlayerAllInventorySlotCount =
            PlayerMainInventorySlotCount + PlayerArmorInventorySlotCount + PlayerOffHandInventorySlotCount;
    public static final double DefaultAttackSpeed = 4D;

    public static final OpenItem OpenItem = new OpenItem();

    public static Number PerSecond(Number value) {
        return value.floatValue() / TPS;
    }

    public enum AttrModifierType {
        DeltaAddition(0),
        RateAddition(1),
        CompoundInterest(2);

        public final int type;

        AttrModifierType(int type) {
            this.type = type;
        }
    }

    public static final class Color {
        public static final int BlackColor = 0x404040;
    }

    public static final class DamageType {
        public static final String Mob = "mob";
        public static final String Player = "player";
    }

    public static final class I18n {
        public static final String Tile = "tile";
        public static final String Tooltip = "tooltip";
        public static final String Item = "item";
        public static final String Command = "commands";
        public static final String Command_Usage = "usage";
        public static final String Hotkey = "key";
        public static final String Potion = "potion";
        public static final String Effect = "effect";
        public static final String Category = "categories";
        public static final String Name = "name";
    }

    public static final class Resources {
        public static final String Textures = "textures";
        public static final String GUI = "gui";
        public static final String Container = "container";
    }
}

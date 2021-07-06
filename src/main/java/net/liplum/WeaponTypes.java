package net.liplum;

import net.liplum.api.registeies.WeaponTypeRegistry;
import net.liplum.api.weapon.WeaponType;

public final class WeaponTypes {
    public static final WeaponType Lance = WeaponTypeRegistry.register(
            new WeaponType(Names.Item.Lance.TypeName));

    public static final WeaponType Harp = WeaponTypeRegistry.register(
            new WeaponType(Names.Item.Harp.TypeName));

    public static final WeaponType BattleAxe = WeaponTypeRegistry.register(
            new WeaponType(Names.Item.BattleAxe.TypeName));
}

package net.liplum;

import net.liplum.api.weapon.WeaponType;

public final class WeaponTypes {
    public static final WeaponType Lance = new WeaponType(Names.Item.Lance.TypeName)
            .setWeaponSkillPredicate((world, player, weapon) -> player.onGround && player.isSneaking());

    public static final WeaponType Harp = new WeaponType(Names.Item.Harp.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> player.isSneaking()));

    public static final WeaponType BattleAxe = new WeaponType(Names.Item.BattleAxe.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> !player.isSneaking()));

    public static final WeaponType RangedWeapon = new WeaponType(Names.Item.RangedWeapon.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> player.isSneaking()));

    public static final WeaponType Bow = new WeaponType(Names.Item.Bow.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> player.isSneaking()));

    public static final WeaponType Sword = new WeaponType(Names.Item.Sword.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> true));
}

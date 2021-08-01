package net.liplum;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.annotations.OnlyWhenInitialization;
import net.liplum.api.weapon.WeaponType;

@LongSupport
public final class WeaponTypes {
    public static final WeaponType Lance = new WeaponType(Names.Item.Lance.TypeName)
            .setWeaponSkillPredicate((world, player, weapon) -> player.onGround && player.isSneaking());

    public static final WeaponType Harp = new WeaponType(Names.Item.Harp.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> player.isSneaking()));

    public static final WeaponType BattleAxe = new WeaponType(Names.Item.BattleAxe.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> !player.isSneaking()));

    public static final WeaponType Ranged = new WeaponType(Names.Item.Ranged.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> player.isSneaking()));

    public static final WeaponType Bow = new WeaponType(Names.Item.Bow.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> player.isSneaking()));

    public static final WeaponType Sword = new WeaponType(Names.Item.Sword.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> true));

    public static final WeaponType MagickWand = new WeaponType(Names.Item.MagickWand.TypeName)
            .setWeaponSkillPredicate(((world, player, weapon) -> true));

    //You must call it to load this class and all the static fields.
    @OnlyWhenInitialization
    public static void load() {

    }
}

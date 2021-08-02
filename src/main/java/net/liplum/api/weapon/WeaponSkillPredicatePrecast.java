package net.liplum.api.weapon;

import net.liplum.api.annotations.LongSupport;

@LongSupport
public final class WeaponSkillPredicatePrecast {
    public static final IWeaponSkillPredicate AlwaysTrue = (world, player, weapon) -> true;
    public static final IWeaponSkillPredicate OnGround = (world, player, weapon) -> player.onGround;
    public static final IWeaponSkillPredicate Sneaking = (world, player, weapon) -> player.isSneaking();
}

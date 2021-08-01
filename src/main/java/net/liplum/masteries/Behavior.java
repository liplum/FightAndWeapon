package net.liplum.masteries;

import net.liplum.Names;

import javax.annotation.Nonnull;

public class Behavior {
    @Nonnull
    public static final Behavior KillEntity = new Behavior(Names.Behavior.KillEntity);
    @Nonnull
    public static final Behavior HealWeapon = new Behavior(Names.Behavior.HealWeapon);
    @Nonnull
    public static final Behavior DamageWeapon = new Behavior(Names.Behavior.DamageWeapon);
    @Nonnull
    public static final Behavior CauseDamage = new Behavior(Names.Behavior.CauseDamage);
    @Nonnull
    public static final Behavior DestroyBlock = new Behavior(Names.Behavior.DestroyBlock);
    @Nonnull
    public static final Behavior ReleaseWeaponSkill = new Behavior(Names.Behavior.ReleaseWeaponSkill);
    @Nonnull
    public static final Behavior UseWeapon = new Behavior(Names.Behavior.UseWeapon);
    @Nonnull
    public static final Behavior TriggerPassiveSkill = new Behavior(Names.Behavior.TriggerPassiveSkill);
    @Nonnull
    public static final Behavior Injury = new Behavior(Names.Behavior.Injury);

    @Nonnull
    public final String registerName;

    public Behavior(@Nonnull String registerName) {
        this.registerName = registerName;
    }
}

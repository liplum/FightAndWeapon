package net.liplum.masteries;

import net.liplum.Names;
import org.jetbrains.annotations.NotNull;

public class Behavior {
    @NotNull
    public static final Behavior KillEntity = new Behavior(Names.Behavior.KillEntity);
    @NotNull
    public static final Behavior HealWeapon = new Behavior(Names.Behavior.HealWeapon);
    @NotNull
    public static final Behavior DamageWeapon = new Behavior(Names.Behavior.DamageWeapon);
    @NotNull
    public static final Behavior CauseDamage = new Behavior(Names.Behavior.CauseDamage);
    @NotNull
    public static final Behavior DestroyBlock = new Behavior(Names.Behavior.DestroyBlock);
    @NotNull
    public static final Behavior ReleaseWeaponSkill = new Behavior(Names.Behavior.ReleaseWeaponSkill);
    @NotNull
    public static final Behavior UseWeapon = new Behavior(Names.Behavior.UseWeapon);
    @NotNull
    public static final Behavior TriggerPassiveSkill = new Behavior(Names.Behavior.TriggerPassiveSkill);
    @NotNull
    public static final Behavior Injury = new Behavior(Names.Behavior.Injury);

    @NotNull
    public final String registerName;

    public Behavior(@NotNull String registerName) {
        this.registerName = registerName;
    }
}

package net.liplum.lib;

import net.liplum.Vanilla;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntityDamageSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FawDamage extends EntityDamageSource {
    @Nonnull
    private final WeaponBaseItem weapon;
    @Nullable
    private IGemstone gemstone;
    @Nullable
    private Modifier modifier;

    private final EntityLivingBase attacker;

    private FawDamage(String damageType, @Nonnull EntityLivingBase attacker, @Nonnull WeaponBaseItem weapon) {
        super(damageType, attacker);
        this.attacker = attacker;
        this.weapon = weapon;
    }

    @Nonnull
    public static FawDamage byPlayer(@Nonnull EntityPlayer playerAttacker, @Nonnull WeaponBaseItem weapon) {
        return new FawDamage(Vanilla.DamageType.Player, playerAttacker, weapon);
    }

    @Nonnull
    public static FawDamage byMob(@Nonnull EntityLivingBase entityAttacker, @Nonnull WeaponBaseItem weapon) {
        return new FawDamage(Vanilla.DamageType.Mob, entityAttacker, weapon);
    }

    @Nonnull
    public EntityLivingBase getAttacker() {
        return attacker;
    }

    @Nonnull
    public WeaponBaseItem getWeapon() {
        return weapon;
    }

    @Nonnull
    public FawDamage setGemstone(@Nonnull IGemstone gemstone, @Nonnull Modifier modifier) {
        this.gemstone = gemstone;
        this.modifier = modifier;
        return this;
    }

    @Nullable
    public IGemstone getGemstone() {
        return gemstone;
    }

    @Nonnull
    public FawDamage setGemstone(@Nonnull IGemstone gemstone) {
        this.gemstone = gemstone;
        this.modifier = gemstone.getModifierOf(weapon.getCore());
        return this;
    }

    @Nullable
    public Modifier getModifier() {
        return modifier;
    }
}

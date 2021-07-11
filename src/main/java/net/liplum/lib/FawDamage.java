package net.liplum.lib;

import net.liplum.Vanilla;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntityDamageSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FawDamage extends EntityDamageSource {
    @Nonnull
    private final WeaponCore weaponCore;
    @Nullable
    private IGemstone gemstone;
    @Nullable
    private Modifier modifier;

    private FawDamage(String damageType, @Nonnull EntityLivingBase attacker, @Nonnull WeaponCore weaponCore) {
        super(damageType, attacker);
        this.weaponCore = weaponCore;
    }

    @Nonnull
    public static FawDamage byPlayer(@Nonnull EntityPlayer playerAttacker, @Nonnull WeaponCore weaponCore) {
        return new FawDamage(Vanilla.DamageType.Player, playerAttacker, weaponCore);
    }

    @Nonnull
    public static FawDamage byMob(@Nonnull EntityLivingBase entityAttacker, @Nonnull WeaponCore weaponCore) {
        return new FawDamage(Vanilla.DamageType.Mob, entityAttacker, weaponCore);
    }

    @Nonnull
    public EntityLivingBase getAttacker() {
        return (EntityLivingBase) damageSourceEntity;
    }

    @Nonnull
    public WeaponCore getWeaponCore() {
        return weaponCore;
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
        this.modifier = gemstone.getModifierOf(weaponCore);
        return this;
    }

    @Nullable
    public Modifier getModifier() {
        return modifier;
    }
}

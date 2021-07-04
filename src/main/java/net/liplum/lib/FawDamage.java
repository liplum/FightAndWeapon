package net.liplum.lib;

import net.liplum.Vanilla;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntityDamageSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FawDamage extends EntityDamageSource {
    @Nonnull
    private final IWeaponCore weaponCore;
    @Nullable
    private IGemstone gemstone;
    @Nullable
    private IModifier<?> modifier;

    private FawDamage(String damageType, @Nonnull EntityLivingBase attacker, @Nonnull IWeaponCore weaponCore) {
        super(damageType, attacker);
        this.weaponCore = weaponCore;
    }

    public static FawDamage byPlayer(@Nonnull EntityPlayer playerAttacker, @Nonnull IWeaponCore weaponCore) {
        return new FawDamage(Vanilla.DamageType.Player, playerAttacker, weaponCore);
    }

    public static FawDamage byMob(@Nonnull EntityLivingBase entityAttacker, @Nonnull IWeaponCore weaponCore) {
        return new FawDamage(Vanilla.DamageType.Mob, entityAttacker, weaponCore);
    }

    public EntityLivingBase getAttacker() {
        return (EntityLivingBase) damageSourceEntity;
    }

    @Nonnull
    public IWeaponCore getWeaponCore() {
        return weaponCore;
    }

    public FawDamage setGemstone(@Nonnull IGemstone gemstone) {
        this.gemstone = gemstone;
        this.modifier = gemstone.getModifierOf(weaponCore);
        return this;
    }

    public FawDamage setGemstone(@Nonnull IGemstone gemstone, @Nonnull IModifier<?> modifier) {
        this.gemstone = gemstone;
        this.modifier = modifier;
        return this;
    }

    @Nullable
    public IGemstone getGemstone() {
        return gemstone;
    }

    @Nullable
    public IModifier<?> getModifier() {
        return modifier;
    }
}

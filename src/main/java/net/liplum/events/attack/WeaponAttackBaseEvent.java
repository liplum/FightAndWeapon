package net.liplum.events.attack;

import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class WeaponAttackBaseEvent extends Event {
    protected final World world;
    protected final EntityLivingBase attacker;
    protected final Entity target;
    protected final IWeaponCore weaponCore;
    protected final IModifier<?> modifier;
    protected final ItemStack itemStack;
    protected DamageSource damageSource;
    protected float damage;

    public WeaponAttackBaseEvent(World world, EntityLivingBase attacker, Entity target, IWeaponCore weaponCore, IModifier<?> modifier, ItemStack itemStack) {
        this.world = world;
        this.attacker = attacker;
        this.target = target;
        this.weaponCore = weaponCore;
        this.modifier = modifier;
        this.itemStack = itemStack;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public void setDamageSource(DamageSource damageSource) {
        this.damageSource = damageSource;
    }

    public World getWorld() {
        return world;
    }

    public EntityLivingBase getAttacker() {
        return attacker;
    }

    public Entity getTarget() {
        return target;
    }

    public IWeaponCore getWeaponCore() {
        return weaponCore;
    }

    public IModifier<?> getModifier() {
        return modifier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
